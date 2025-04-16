package com.shipsmartapp.delivery_choosing.di

import com.core.di.NetworkDeliveryModule
import com.core.domain.DeliveryService
import com.shipsmartapp.delivery_choosing.data.network.DeliveryRepositoryImpl
import com.shipsmartapp.delivery_choosing.domain.repository.DeliveryRepository
import com.shipsmartapp.delivery_choosing.domain.usecases.GetDeliveryCostUseCase
import dagger.Module
import dagger.Provides

@Module(includes = [NetworkDeliveryModule::class, DeliveryChooserModule::class])
class DeliveryChoosingFeatureModule

@Module
class DeliveryChooserModule {
    @Provides
    fun provideDeliveryRepositoryImpl(
        service: DeliveryService
    ): DeliveryRepository {
        return DeliveryRepositoryImpl(service)
    }

    @Provides
    fun provideGetDeliveryCostUseCase(
        repository: DeliveryRepository
    ): GetDeliveryCostUseCase {
        return GetDeliveryCostUseCase(repository)
    }
}


