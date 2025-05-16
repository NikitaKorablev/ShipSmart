package com.core.delivery_network.data.companies_repos

import com.core.delivery_network.data.DeliveryData

sealed class DeliveryResponse {
    data class Accept(val data: DeliveryData): DeliveryResponse()
    data class Error(val message: String): DeliveryResponse()
}
