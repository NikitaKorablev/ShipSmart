package com.core.delivery_network.domain

import com.core.delivery_network.data.network.*
import retrofit2.http.GET
import retrofit2.http.Url

interface BoxberryDeliveryService {
    @GET
    suspend fun getData(
        @Url url: String
    ): BoxberryApiResponse
}

interface CDEKDeliveryService {
    @GET
    suspend fun getData(
        @Url url: String
    ): CDEKApiResponse
}
