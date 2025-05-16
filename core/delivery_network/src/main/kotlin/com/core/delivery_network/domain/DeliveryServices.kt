package com.core.delivery_network.domain

import com.core.delivery_network.data.companies_repos.cost_response_data.BoxberryApiResponse
import com.core.delivery_network.data.companies_repos.cost_response_data.CDEKApiResponse
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
