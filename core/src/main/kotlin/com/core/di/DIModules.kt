package com.core.di

//import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.core.BuildConfig
import com.core.data.storage.SupabaseUserStorage
import com.core.domain.DeliveryService
import com.core.domain.UserStorageInterface
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

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
        @Named("apiKey") key: String,
        @Named("apiUrl") url: String
    ) : UserStorageInterface {
        return SupabaseUserStorage(key=key, url=url)
    }
}

@Module
class NetworkDeliveryModule {
    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl(): String = "https://google.com"

    @Provides
    fun provideDeliveryService(
        @Named("BaseUrl") url: String
    ): DeliveryService {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(CoroutineCallAdapterFactory())
//            .client(client)
            .build()
            .create(DeliveryService::class.java)
    }
}