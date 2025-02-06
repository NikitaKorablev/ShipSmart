package com.app.domain

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    fun getData(@Url url: String): Call<ApiResponse>
}