package com.core.delivery_network.di

import com.core.delivery_network.domain.BoxberryDeliveryService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
class NetworkDeliveryModule {
    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl(): String = "https://google.com"

    @Provides
    fun provideBoxberryDeliveryService (
        @Named("BaseUrl") url: String
    ): BoxberryDeliveryService {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BoxberryDeliveryService::class.java)
    }
}