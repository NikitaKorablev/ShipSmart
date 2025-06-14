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
import javax.inject.Inject

class CDEKDeliveryRepositoryImpl @Inject constructor(
    private var deliveryService: CDEKDeliveryService
) : DeliveryRepository {
    override val company = CDEK()

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

            val response = deliveryService.getCostData(url)
            if (response.data.isEmpty())
                DeliveryResponse.Error("Getting delivery cost exception: Cost not found")
            val delivery = getCheapestDelivery(response.data)

            val cost = delivery.minPrice
            val deliveryTime = delivery.minDays

            DeliveryResponse.Accept(
                DeliveryData(
                    name = company.name,
                    cost = cost.toString(),
                    deliveryTime = deliveryTime.toString(),
                    img = company.imgResource
                )
            )
        } catch (e: Exception) {
            DeliveryResponse.Error("Getting CDEK delivery cost exception: ${e.message}")
        }
    }

    private fun getCheapestDelivery(data: List<DeliveryType>): DeliveryType {
        return data.minBy { it.minDays }.let { minDaysTariff ->
            data.filter { it.minDays == minDaysTariff.minDays }
                .minBy { it.minPrice }
        }
    }

    companion object {
        const val TAG = "CDEKDeliveryRepo"
    }
}