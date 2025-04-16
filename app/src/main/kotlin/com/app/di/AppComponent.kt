package com.app.di

import android.app.Application
import com.core.utils.Router
import com.shipsmartapp.delivery_choosing.di.DeliveryDeps
import com.shipsmartapp.login.di.LoginDeps
import com.shipsmartapp.package_size_collector.di.PackageCollectorDeps
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent: LoginDeps, PackageCollectorDeps, DeliveryDeps {

    override val router: Router

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}