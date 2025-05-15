package com.core.delivery_network.data.companies_repos

import android.net.http.HttpException
import android.os.Build
import android.annotation.SuppressLint
import androidx.annotation.RequiresExtension
import com.core.delivery_network.data.DeliveryData
import com.core.delivery_network.data.PackageExtraParams
import com.core.delivery_network.domain.BoxberryDeliveryService
import com.core.delivery_network.data.companies.Boxberry
import com.core.delivery_network.domain.repository.DeliveryRepository
import javax.inject.Inject

class BoxberryDeliveryRepositoryImpl @Inject constructor(
    private var deliveryService: BoxberryDeliveryService
): DeliveryRepository {
    override val company = Boxberry()

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getDeliveryCost(
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