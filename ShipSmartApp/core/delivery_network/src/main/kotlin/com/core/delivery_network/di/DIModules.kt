package com.core.delivery_network.di

import com.core.delivery_network.data.companies_repos.BoxberryDeliveryRepositoryImpl
import com.core.delivery_network.data.companies_repos.CDEKDeliveryRepositoryImpl
import com.core.delivery_network.domain.delivery_services.CDEKDeliveryService
import com.core.delivery_network.domain.repository.DeliveryReposList
import com.core.delivery_network.domain.repository.DeliveryRepository
import com.core.delivery_network.domain.delivery_services.BoxberryDeliveryService
import dagger.Module
import dagger.Provides
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkDeliveryModule {
    @Provides
    @Named("BoxberryBaseUrl")
    fun provideBoxberryBaseUrl(): String = "https://boxberry.ru/"

    @Provides
    @Named("CDEKBaseUrl")
    fun provideCDEKBaseUrl(): String = "https://cdek.ru/"

    @Provides
    @Singleton
    fun provideBoxberryDeliveryService(
        @Named("BoxberryBaseUrl") url: String
    ): BoxberryDeliveryService {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BoxberryDeliveryService::class.java)
    }

    @Provides
    @Singleton
    fun provideCDEKDeliveryService(
        @Named("CDEKBaseUrl") url: String,
    ): CDEKDeliveryService {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CDEKDeliveryService::class.java)
    }
}

@Module
class DeliveryCompaniesModule {
    @Provides
    @Singleton
    fun provideBoxberryDeliveryRepository(
        service: BoxberryDeliveryService
    ): DeliveryRepository {
        return BoxberryDeliveryRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideCDEKDeliveryRepository(
        service: CDEKDeliveryService
    ): DeliveryRepository {
        return CDEKDeliveryRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideDeliveryReposList(
        boxberryRepo: BoxberryDeliveryRepositoryImpl,
        cdekRepo: CDEKDeliveryRepositoryImpl
    ): DeliveryReposList {
        return DeliveryReposList(boxberryRepo, cdekRepo)
    }
}

