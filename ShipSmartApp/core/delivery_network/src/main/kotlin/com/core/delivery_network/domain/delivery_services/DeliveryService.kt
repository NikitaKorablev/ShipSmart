package com.core.delivery_network.domain.delivery_services

import com.core.delivery_network.data.request_data.ApiRequest
import com.core.delivery_network.data.response_data.ApiResponse

interface DeliveryService {
    suspend fun getCostData(url: String): ApiResponse
//    suspend fun getCostData(
//        url: String,
////        data: ApiRequest,
//    ): ApiResponse
    suspend fun getCityCodeData(url: String): ApiResponse
}