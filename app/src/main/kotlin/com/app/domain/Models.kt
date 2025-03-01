package com.app.domain

data class ApiResponse(
    val status: Int,
    val err: List<Any>,
    val data: List<DeliveryData>
)

data class DeliveryData(
    val delivery_type: Int,
    val cd_price: Int,
    val cost: Int,
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