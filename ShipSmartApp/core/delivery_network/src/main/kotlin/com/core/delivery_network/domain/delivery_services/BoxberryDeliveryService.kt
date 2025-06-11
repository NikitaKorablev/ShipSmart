package com.core.delivery_network.domain.delivery_services

import com.core.delivery_network.data.response_data.city_response_data.BoxberryCityDataResponse
import com.core.delivery_network.data.response_data.cost_response_data.BoxberryApiResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface BoxberryDeliveryService: DeliveryService {
    @GET
    override suspend fun getCostData(
        @Url url: String
    ): BoxberryApiResponse

    @GET
    override suspend fun getCityCodeData(
        @Url url: String
    ): BoxberryCityDataResponse
}