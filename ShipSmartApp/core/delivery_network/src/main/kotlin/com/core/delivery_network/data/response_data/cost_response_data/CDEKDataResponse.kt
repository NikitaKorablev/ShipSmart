package com.core.delivery_network.data.response_data.cost_response_data

import com.core.delivery_network.data.response_data.ApiResponse

data class CDEKApiResponse(
    val data: List<DeliveryType>,
    val message: String,
    val status: String,
): ApiResponse

data class DeliveryType(
    val description: String,
    val id: Int,
    val maxDays: Int,
    val minDays: Int,
    val minPrice: Int,
    val placing: Int,
    val tariffs: List<Any>
)

