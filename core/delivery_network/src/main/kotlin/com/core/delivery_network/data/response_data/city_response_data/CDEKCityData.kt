package com.core.delivery_network.data.response_data.city_response_data

import com.core.delivery_network.data.response_data.ApiResponse

data class CDEKCityDataResponse(
    val data: List<CDEKCityData>
): ApiResponse

data class CDEKCityData(
    val id: Int,
    val uuid: String,
    val name: String,
    val countryCode: Int,
    val fullName: String,
    val regionCode: Int,
    val latitude: Double,
    val longitude: Double
)