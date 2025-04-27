package com.shipsmartapp.delivery_choosing.data.network

import android.annotation.SuppressLint
import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.core.delivery_network.data.PackageExtraParams
import com.core.delivery_network.domain.BoxberryDeliveryService
import com.shipsmartapp.delivery_choosing.data.DeliveryCompany
import com.shipsmartapp.delivery_choosing.data.DeliveryData
import com.shipsmartapp.delivery_choosing.domain.repository.DeliveryRepository


class DeliveryRepositoryImpl(
    private var deliveryService: BoxberryDeliveryService
): DeliveryRepository {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getDeliveryCost(
        company: DeliveryCompany,
        packageParams: PackageExtraParams
    ): NetworkResponse {
        return try {
            val url = company.getUrl(packageParams)
            val response = deliveryService.getData(url)

            val data = response.data.find { it.delivery_type == 4 }
            val cost = data?.cost
            val deliveryTime = data?.time

            if (cost != null && deliveryTime != null) {
                val formattedCost = formatCost(cost)
                val deliveryData = DeliveryData(
                    name=company.name,
                    cost=formattedCost,
                    deliveryTime=deliveryTime
                )
                NetworkResponse.Accept(deliveryData)
            } else {
                NetworkResponse.Error("Getting delivering cost exception:\n     Cost not found")
            }
        } catch (e: HttpException) {
            NetworkResponse.Error("Getting delivering cost exception:\n     ${e.message.toString()}")
        } catch (e: Exception) {
            NetworkResponse.Error("Getting delivering cost exception:\n     ${e.message.toString()}")
        }
    }

    @SuppressLint("DefaultLocale")
    private fun formatCost(cost: Double): String {
        return String.format("%.2f", cost/100.0)
    }
}