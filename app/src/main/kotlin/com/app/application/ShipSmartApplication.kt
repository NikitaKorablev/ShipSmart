package com.app.application

import android.app.Application
import com.app.di.AppComponent
import com.app.di.DaggerAppComponent
import com.shipsmartapp.login.di.DaggerLoginComponent
import com.shipsmartapp.login.di.LoginComponent
import com.shipsmartapp.login.di.LoginDepsProvider

class ShipSmartApplication: Application(), LoginDepsProvider {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }

    override fun getLoginComponent(): LoginComponent {
        return DaggerLoginComponent.builder().deps(appComponent).build()
    }
}