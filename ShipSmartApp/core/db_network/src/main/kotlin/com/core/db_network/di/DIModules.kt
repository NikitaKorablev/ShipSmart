package com.core.db_network.di

import com.core.db_network.data.storage.SupabaseUserStorage
import dagger.Module
import dagger.Provides
import javax.inject.Named
import com.core.db_network.BuildConfig
import com.core.db_network.domain.UserStorageInterface

@Module
class SupabaseModule {
    @Provides
    @Named("apiKey")
    fun provideSupabaseKey(): String = BuildConfig.KEY

    @Provides
    @Named("apiUrl")
    fun provideSupabaseUrl(): String = BuildConfig.URL

    @Provides
    fun provideUserStorageInterface(
        @Named("apiKey") key: String,
        @Named("apiUrl") url: String
    ): UserStorageInterface {
        return SupabaseUserStorage(key=key, url=url)
    }
}