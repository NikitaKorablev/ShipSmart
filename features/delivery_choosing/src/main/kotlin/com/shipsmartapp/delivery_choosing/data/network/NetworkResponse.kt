package com.shipsmartapp.delivery_choosing.data.network

import com.core.data.network.ApiResponse

sealed class NetworkResponse {
    data class Accept(val cost: String): NetworkResponse()
    data class Error(val message: String): NetworkResponse()
}
