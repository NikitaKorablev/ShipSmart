package com.core.delivery_network.data.companies_repos.city_response_data

import com.core.delivery_network.data.CityParams

sealed class CityResponse {
    data class Accept(val data: CityParams): CityResponse()
    data class Error(val message: String): CityResponse()
}
