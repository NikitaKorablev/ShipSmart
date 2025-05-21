package com.core.delivery_network.data.companies_repos

import android.net.http.HttpException
import android.os.Build
import android.annotation.SuppressLint
import androidx.annotation.RequiresExtension
import com.core.delivery_network.data.CityParams
import com.core.delivery_network.data.DeliveryData
import com.core.delivery_network.data.PackageParams
import com.core.delivery_network.data.companies.Boxberry
import com.core.delivery_network.data.response_data.DeliveryResponse
import com.core.delivery_network.data.response_data.city_response_data.BoxberryCity
import com.core.delivery_network.data.response_data.city_response_data.BoxberryCountry
import com.core.delivery_network.data.response_data.city_response_data.CityResponse
import com.core.delivery_network.domain.repository.DeliveryRepository
import com.core.delivery_network.domain.delivery_services.BoxberryDeliveryService
import javax.inject.Inject

class BoxberryDeliveryRepositoryImpl @Inject constructor(
    private var deliveryService: BoxberryDeliveryService
): DeliveryRepository {
    override val company = Boxberry()

    override suspend fun getCitiesData(packageParams: PackageParams): CityResponse {
        return try {
            val url = company.getCityUrl()
            val response = deliveryService.getCityCodeData(url)

            val senderCountry = response.country
                .find { it.name == packageParams.cityParams.senderCountry }
            val receiverCountry = response.country
                .find { it.name == packageParams.cityParams.receiverCountry }
            val senderCity = response.cities
                .find { it.name == packageParams.cityParams.senderCity }
            val receiverCity = response.cities
                .find { it.name == packageParams.cityParams.receiverCity }

            when {
                senderCountry == null -> CityResponse.Error("Sender country is undefined")
                receiverCountry == null -> CityResponse.Error("Receiver country is undefined")
                senderCity == null -> CityResponse.Error("Sender city is undefined")
                receiverCity == null -> CityResponse.Error("Receiver city is undefined")
                else -> CityResponse.Accept(
                    CityParams(
                        senderCity = senderCity.code,
                        senderCountry = senderCountry.code,
                        receiverCity = receiverCity.code,
                        receiverCountry = receiverCountry.code
                    )
                )
            }
        } catch (e: Exception) {
            CityResponse.Error("Getting city code exception:\n\t${e.message.toString()}")
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getDeliveryCost(
        packageParams: PackageParams,
    ): DeliveryResponse {
        return try {
            val url = company.getCostUrl(packageParams)
            val response = deliveryService.getCostData(url)

            val data = response.data.find { it.delivery_type == 4 }
            val cost = data?.cost
            val deliveryTime = data?.time

            if (cost != null && deliveryTime != null) {
                val formattedCost = formatCost(cost)
                val deliveryData = DeliveryData(
                    name=company.name,
                    cost=formattedCost,
                    deliveryTime=deliveryTime,
                    img = company.imgResource
                )
                DeliveryResponse.Accept(deliveryData)
            } else {
                DeliveryResponse.Error("Getting delivering cost exception:\n\tCost not found")
            }
        } catch (e: HttpException) {
            DeliveryResponse.Error("Getting delivering cost exception:\n\t${e.message.toString()}")
        } catch (e: Exception) {
            DeliveryResponse.Error("Getting delivering cost exception:\n\t${e.message.toString()}")
        }
    }

    @SuppressLint("DefaultLocale")
    private fun formatCost(cost: Double): String {
        return String.format("%.2f", cost/100.0)
    }
}