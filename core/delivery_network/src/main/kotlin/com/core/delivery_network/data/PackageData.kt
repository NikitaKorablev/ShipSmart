package com.core.delivery_network.data

data class PackageData(
    val height: String,
    val width: String,
    val length: String,

    val cityData: CityData
)

data class CityData(
    val senderCity: String,
    val senderCountry: String,
    val receiverCity: String,
    val receiverCountry: String
)