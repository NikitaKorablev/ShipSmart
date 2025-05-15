package com.core.delivery_network.data.companies_repos

import android.net.http.HttpException
import android.os.Build
import android.annotation.SuppressLint
import androidx.annotation.RequiresExtension
import com.core.delivery_network.data.DeliveryData
import com.core.delivery_network.data.PackageExtraParams
import com.core.delivery_network.data.companies.CDEK
import com.core.delivery_network.domain.CDEKDeliveryService
import com.core.delivery_network.domain.repository.DeliveryRepository

class CDEKDeliveryRepositoryImpl(
    private val deliveryService: CDEKDeliveryService
) : DeliveryRepository {
    override val company = CDEK()

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
                    name = company.name,
                    cost = formattedCost,
                    deliveryTime = deliveryTime
                )
                NetworkResponse.Accept(deliveryData)
            } else {
                NetworkResponse.Error("Getting delivery cost exception: Cost not found")
            }
        } catch (e: HttpException) {
            NetworkResponse.Error("Getting delivery cost exception: ${e.message}")
        } catch (e: Exception) {
            NetworkResponse.Error("Getting delivery cost exception: ${e.message}")
        }
    }

    @SuppressLint("DefaultLocale")
    private fun formatCost(cost: Double): String {
        return String.format("%.2f", cost / 100.0)
    }
}