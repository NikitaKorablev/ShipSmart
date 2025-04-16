package com.shipsmartapp.delivery_choosing.domain.repository

import com.shipsmartapp.delivery_choosing.data.network.NetworkResponse

interface DeliveryRepository {
    suspend fun getDeliveryCost(url: String): NetworkResponse
}