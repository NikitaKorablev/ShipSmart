package com.core.delivery_network.data.response_data.cost_response_data

import com.core.delivery_network.data.response_data.ApiResponse

data class CDEKApiResponse(
    val data: List<DeliveryType>,
    val currencyMark: String,
    val isDeliveryWithoutWarehousePossible: Boolean,
    val isInternationalOrder: Boolean
): ApiResponse

data class DeliveryType(
    val id: Int,
    val placing: Int,
    val minPrice: Int,
    val minDays: Int,
    val maxDays: Int,
    val description: String,
    val tariffs: List<Any>
)

