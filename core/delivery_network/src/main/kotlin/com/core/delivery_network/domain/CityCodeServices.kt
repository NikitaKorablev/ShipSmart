package com.core.delivery_network.domain

import com.core.delivery_network.data.companies_repos.city_response_data.BoxberryCityData
import retrofit2.http.GET
import retrofit2.http.Url

interface BoxberryCityCodeService {
    @GET
    suspend fun getData(
        @Url url: String
    ): BoxberryCityData
}