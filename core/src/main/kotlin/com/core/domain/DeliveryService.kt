package com.core.domain

import com.core.data.network.ApiResponse
import kotlinx.coroutines.Deferred

import retrofit2.http.GET
import retrofit2.http.Url

interface DeliveryService {
    @GET
    suspend fun getData(
        @Url url: String
    ): ApiResponse
}