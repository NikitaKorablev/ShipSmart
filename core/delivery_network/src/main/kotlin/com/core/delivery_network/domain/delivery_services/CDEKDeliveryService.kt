package com.core.delivery_network.domain.delivery_services

import com.core.delivery_network.data.request_data.ApiRequest
import com.core.delivery_network.data.response_data.city_response_data.CDEKCityDataResponse
import com.core.delivery_network.data.response_data.cost_response_data.CDEKApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Url

interface CDEKDeliveryService: DeliveryService {
    @POST
    override suspend fun getCostData(
        @Url url: String,
        @HeaderMap headers: Map<String, String>,
        @Body request: ApiRequest,
    ): CDEKApiResponse

    @GET
    override suspend fun getCityCodeData(
        @Url url: String
    ): CDEKCityDataResponse
}
