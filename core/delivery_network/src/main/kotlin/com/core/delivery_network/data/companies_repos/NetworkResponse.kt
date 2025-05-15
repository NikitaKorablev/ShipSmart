package com.core.delivery_network.data.companies_repos

import com.core.delivery_network.data.DeliveryData

sealed class NetworkResponse {
    data class Accept(val data: DeliveryData): NetworkResponse()
    data class Error(val message: String): NetworkResponse()
}
