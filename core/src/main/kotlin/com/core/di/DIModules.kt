package com.core.di

import com.core.BuildConfig
import com.core.data.storage.SupabaseUserStorage
import com.core.domain.UserStorageInterface
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class SupabaseModule {
    @Provides
    @Named("apiKey")
    fun provideSupabaseKey(): String = BuildConfig.SB_KEY

    @Provides
    @Named("apiUrl")
    fun provideSupabaseUrl(): String = BuildConfig.SB_URL

    @Provides
    fun provideUserStorageInterface(
//        @Named("apiKey") key: String,
//        @Named("apiUrl") url: String
    ) : UserStorageInterface {
        return SupabaseUserStorage()
    }
}