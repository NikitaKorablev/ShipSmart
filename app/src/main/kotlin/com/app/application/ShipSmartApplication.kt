package com.app.application

import android.app.Application
import com.app.di.AppComponent
import com.app.di.DaggerAppComponent
import com.shipsmartapp.delivery_choosing.di.DaggerDeliveryComponent
import com.shipsmartapp.delivery_choosing.di.DeliveryComponent
import com.shipsmartapp.delivery_choosing.di.DeliveryDepsProvider
import com.shipsmartapp.login.di.DaggerLoginComponent
import com.shipsmartapp.login.di.LoginComponent
import com.shipsmartapp.login.di.LoginDepsProvider
import com.shipsmartapp.package_size_collector.di.DaggerPackageCollectorComponent
import com.shipsmartapp.package_size_collector.di.PackageCollectorComponent
import com.shipsmartapp.package_size_collector.di.PackageCollectorDepsProvider

class ShipSmartApplication: Application(), LoginDepsProvider, PackageCollectorDepsProvider,
DeliveryDepsProvider {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }

    override fun getLoginComponent(): LoginComponent {
        return DaggerLoginComponent.builder().deps(appComponent).build()
    }

    override fun getPackageCollectorComponent(): PackageCollectorComponent {
        return DaggerPackageCollectorComponent.builder().deps(appComponent).build()
    }

    override fun getDeliveryComponent(): DeliveryComponent {
        return DaggerDeliveryComponent.builder().deps(appComponent).build()
    }
}