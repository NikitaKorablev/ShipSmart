package com.core.delivery_network.data

data class PackageParams(
    val height: String,
    val width: String,
    val length: String,

    val cityParams: CityParams
)

data class CityParams(
    val senderCity: String,
    val senderCountry: String,
    val receiverCity: String,
    val receiverCountry: String
)