package com.shipsmartapp.delivery_choosing.data.network

import android.annotation.SuppressLint
import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.core.domain.DeliveryService
import com.shipsmartapp.delivery_choosing.domain.repository.DeliveryRepository


class DeliveryRepositoryImpl(
    private var deliveryService: DeliveryService
): DeliveryRepository {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getDeliveryCost(url: String): NetworkResponse {
        return try {
            val response = deliveryService.getData(url)
            val cost = response.data.find { it.delivery_type == 4 }?.cost
            if (cost != null) {
                val formattedCost = formatCost(cost)
                NetworkResponse.Accept(formattedCost)
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