package com.core.delivery_network.data.companies_repos

import android.annotation.SuppressLint
import android.util.Log
import com.core.delivery_network.data.CityParams
import com.core.delivery_network.data.DeliveryData
import com.core.delivery_network.data.PackageParams
import com.core.delivery_network.data.response_data.city_response_data.CDEKCityData
import com.core.delivery_network.data.companies.CDEK
import com.core.delivery_network.data.request_data.CDEKDeliveryRequest
import com.core.delivery_network.data.request_data.RequestPackage
import com.core.delivery_network.data.response_data.DeliveryResponse
import com.core.delivery_network.data.response_data.city_response_data.CityResponse
import com.core.delivery_network.data.response_data.cost_response_data.DeliveryType
import com.core.delivery_network.domain.delivery_services.CDEKDeliveryService
import com.core.delivery_network.domain.repository.DeliveryRepository
import java.util.UUID
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class CDEKDeliveryRepositoryImpl @Inject constructor(
    private var deliveryService: CDEKDeliveryService
) : DeliveryRepository {
    override val company = CDEK()

    private fun getSessionCookie(): String {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(company.getCityUrl("Москва")) // URL для получения куки
            .addHeader("Cache-Control", "no-cache")
            .addHeader("content-type", "application/json")
            .addHeader("Postman-Token", postmanToken)
            .get()
            .build()

        val c = "spid=1749457415299_ce06125d698370a5b81d15f6e0682f08_pxerh4wre81n2qfq; spsc=1749457415299_be201039b498541f1920e87122feb38a_y9TzJJr9MiABUNI9HiCxEZm58xJzlUe5CWv8nGOFELAZ"
        return c
//        return try {
//            val response = client.newCall(request).execute()
//            val cookies = response.headers("Set-Cookie")
//            if (cookies.isNotEmpty()) {
//                cookies.joinToString("; ") { cookie ->
//                    cookie.split(";")[0] // Берем только имя и значение
//                }
//            } else {
//                c
//            }
//        } catch (e: Exception) {
//            Log.e(TAG, "Ошибка получения куки: ${e.message}")
//            c
//        }
    }

    val postmanToken = UUID.randomUUID().toString()

    override suspend fun getCitiesData(packageParams: PackageParams): CityResponse {
        return try {
            val senderCity = getCityData(packageParams.cityParams.senderCity)
            val receiverCity = getCityData(packageParams.cityParams.receiverCity)

            when {
                senderCity == null -> CityResponse.Error("Sender city is undefined")
                receiverCity == null -> CityResponse.Error("Receiver city is undefined")
                else -> CityResponse.Accept(
                    CityParams(
                        senderCity = senderCity.uuid,
                        receiverCity = receiverCity.uuid,
                        senderCountry = senderCity.countryCode.toString(),
                        receiverCountry = receiverCity.countryCode.toString()
                    )
                )
            }
        } catch (e: Exception) {
            CityResponse.Error("Getting city code exception:\n\t${e.message.toString()}")
        }
    }

    private suspend fun getCityData(cityName: String): CDEKCityData? {
        val url = company.getCityUrl(cityName)
        val dataResp = deliveryService.getCityCodeData(url)

        return dataResp.data.find { it.name == cityName }
    }

    override suspend fun getDeliveryCost(
        packageParams: PackageParams,
    ): DeliveryResponse {
        return try {
            val url = company.getCostUrl(packageParams)

//            val requestBody = CDEKDeliveryRequest(
//                payerType = "sender",
//                currencyMark = "RUB",
//                senderCityId = packageParams.cityParams.senderCity,
//                receiverCityId = packageParams.cityParams.receiverCity,
//                packages = listOf(RequestPackage(
//                    height = packageParams.height.toInt(),
//                    width = packageParams.width.toInt(),
//                    length = packageParams.length.toInt(),
//                    weight = 100
//                ))
//            )

            val requestBody = CDEKDeliveryRequest(
                payerType = "sender",
                currencyMark = "RUB",
                senderCityId = "b7af1c1b-b82c-464d-b744-e12ce0ff5f98",
                receiverCityId = "01581370-81f3-4322-9a28-3418adfabd97",
                packages = listOf(RequestPackage(
                    height = 50,
                    width = 100,
                    length = 100,
                    weight = 10
                ))
            )

            val cookie = getSessionCookie()
            val headers = mapOf(
                "Cache-Control" to "no-cache",
                "content-type" to "application/json",
                "Postman-Token" to postmanToken,
                "Cookie" to cookie,
//                "Cookie" to "spid=1747842532123_f18dbf23da06a52611c4392a17e712d3_mao6ngb702e604ea; spsc=1747921580544_83ff4dd2d02fd4b664406b150deaf3b5_Gio1u.SqVbFiw5puB.DW3p2XQlrUBIFAZc9LQnxDg00Z"
            )

            val response = deliveryService.getCostData(url, headers, requestBody)
            if (response.data.isEmpty())
                DeliveryResponse.Error("Getting delivery cost exception: Cost not found")
            val delivery = getCheapestDelivery(response.data)

            val cost = delivery.minPrice
            val deliveryTime = delivery.minDays

            DeliveryResponse.Accept(
                DeliveryData(
                    name = company.name,
                    cost = formatCost(cost.toDouble()),
                    deliveryTime = deliveryTime.toString(),
                    img = company.imgResource
                )
            )
        } catch (e: Exception) {
            DeliveryResponse.Error("Getting CDEK delivery cost exception: ${e.message}")
        }
    }

    private fun getCheapestDelivery(deliveryTypes: List<DeliveryType>): DeliveryType {
        var delivery: DeliveryType = deliveryTypes[0]

        for (i in 1..<deliveryTypes.size) {
            if (deliveryTypes[i].minPrice < delivery.minPrice)
                delivery = deliveryTypes[i]
        }

        return delivery
    }

    @SuppressLint("DefaultLocale")
    private fun formatCost(cost: Double): String {
        return String.format("%.2f", cost / 100.0)
    }

    companion object {
        const val TAG = "CDEKDeliveryRepo"
    }
}