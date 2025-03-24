package com.app.di

import com.app.utils.AppNavigationComponent
import com.core.utils.Router
import com.shipsmartapp.login.di.LoginModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [LoginModule::class, UtilsModule::class])
class AppModule

@Module
class UtilsModule {
    @Provides
    @Singleton
    fun provideRouter(): Router {
        return AppNavigationComponent()
    }
}
