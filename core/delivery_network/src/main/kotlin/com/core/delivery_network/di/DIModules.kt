package com.core.delivery_network.di

import com.core.delivery_network.data.companies_repos.BoxberryDeliveryRepositoryImpl
import com.core.delivery_network.data.companies_repos.CDEKDeliveryRepositoryImpl
import com.core.delivery_network.domain.BoxberryCityCodeService
import com.core.delivery_network.domain.BoxberryDeliveryService
import com.core.delivery_network.domain.CDEKDeliveryService
import com.core.delivery_network.domain.repository.DeliveryReposList
import com.core.delivery_network.domain.repository.DeliveryRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkDeliveryModule {
    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl(): String = "https://google.com"

    @Provides
    @Singleton
    fun provideBoxberryCityCodeService(
        @Named("BaseUrl") url: String
    ): BoxberryCityCodeService {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BoxberryCityCodeService::class.java)
    }

    @Provides
    @Singleton
    fun provideBoxberryDeliveryService(
        @Named("BaseUrl") url: String
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
        @Named("BaseUrl") url: String
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
        cityCodeService: BoxberryCityCodeService,
        deliveryService: BoxberryDeliveryService
    ): DeliveryRepository {
        return BoxberryDeliveryRepositoryImpl(cityCodeService, deliveryService)
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
        boxberryRepo: BoxberryDeliveryRepositoryImpl
    ): DeliveryReposList {
        return DeliveryReposList(boxberryRepo)
    }
}

