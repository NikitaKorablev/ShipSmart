package com.shipsmartapp.delivery_choosing.di

import com.core.delivery_network.di.DeliveryCompaniesModule
import com.core.delivery_network.di.NetworkDeliveryModule
import com.core.delivery_network.domain.repository.DeliveryReposList
import com.shipsmartapp.delivery_choosing.domain.usecases.GetDeliveryCostUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [
        NetworkDeliveryModule::class,
        DeliveryCompaniesModule::class,
        DeliveryChooserModule::class
    ]
)
class DeliveryChoosingFeatureModule

@Module
class DeliveryChooserModule {
    @Provides
    @Singleton
    fun provideGetDeliveryCostUseCase(
        repositories: DeliveryReposList
    ): GetDeliveryCostUseCase {
        return GetDeliveryCostUseCase(repositories)
    }
}


