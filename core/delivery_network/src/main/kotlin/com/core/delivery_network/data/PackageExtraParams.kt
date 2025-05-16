package com.core.delivery_network.data

data class PackageExtraParams(
    val height: Int,
    val width: Int,
    val length: Int,

    val cityParams: CityParams
)

data class CityParams(
    val senderCity: Int,
    val senderCountry: Int,
    val receiverCity: Int,
    val receiverCountry: Int
)