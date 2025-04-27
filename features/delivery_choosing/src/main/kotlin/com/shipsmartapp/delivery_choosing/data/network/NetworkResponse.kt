package com.shipsmartapp.delivery_choosing.data.network

import com.shipsmartapp.delivery_choosing.data.DeliveryData

sealed class NetworkResponse {
    data class Accept(val data: DeliveryData): NetworkResponse()
    data class Error(val message: String): NetworkResponse()
}
