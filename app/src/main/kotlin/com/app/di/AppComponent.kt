package com.app.di

import android.app.Application
import com.core.utils.Router
import com.shipsmartapp.login.di.LoginDeps
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent: LoginDeps {

    override val router: Router

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}