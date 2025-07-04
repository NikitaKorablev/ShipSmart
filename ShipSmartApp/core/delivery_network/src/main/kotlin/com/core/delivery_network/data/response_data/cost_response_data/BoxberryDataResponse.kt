package com.core.delivery_network.data.response_data.cost_response_data

import com.core.delivery_network.data.response_data.ApiResponse

data class BoxberryApiResponse (
    val status: Int,
    val err: List<Any>,
    val data: List<BoxberryDeliveryData>
): ApiResponse

data class BoxberryDeliveryData(
    val delivery_type: Int,
    val cd_price: Int,
    val cost: Double,
    val base: Int,
    val discount: Int,
    val default_services_cost: Int,
    val services_cost: Int,
    val services: List<Service>,
    val return_cost: Int,
    val time: String
)

data class Service(
    val code: String,
    val name: String
)