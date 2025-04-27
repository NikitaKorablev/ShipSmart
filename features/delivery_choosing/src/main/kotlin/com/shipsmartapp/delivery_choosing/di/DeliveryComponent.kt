package com.shipsmartapp.delivery_choosing.di

import com.core.delivery_network.domain.BoxberryDeliveryService
import com.core.utils.Router
import com.shipsmartapp.delivery_choosing.presentation.DeliveryChooserActivity
import com.shipsmartapp.delivery_choosing.presentation.viewmodel.DeliveryChooserViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [DeliveryChoosingFeatureModule::class],
    dependencies = [DeliveryDeps::class]
)
interface DeliveryComponent {

    val service: BoxberryDeliveryService
    fun inject(loginActivity: DeliveryChooserActivity)
    fun inject(viewModel: DeliveryChooserViewModel)

    @Component.Builder
    interface Builder {
        fun deps(deps: DeliveryDeps): Builder
        fun build(): DeliveryComponent
    }
}

interface DeliveryDeps {
    val router: Router
}

interface DeliveryDepsProvider {
    fun getDeliveryComponent(): DeliveryComponent
}