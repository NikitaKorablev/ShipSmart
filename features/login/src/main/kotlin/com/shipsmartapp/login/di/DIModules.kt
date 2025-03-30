package com.shipsmartapp.login.di

import com.core.di.SupabaseModule
import com.shipsmartapp.login.data.UserRepositoryImpl
import com.core.domain.UserStorageInterface
import com.shipsmartapp.login.domain.repository.UserRepositoryInterface
import com.shipsmartapp.login.domain.usecases.SignInUseCase
import com.shipsmartapp.login.domain.usecases.SignUpUseCase
import dagger.Module
import dagger.Provides

@Module(includes = [SupabaseModule::class])
class LoginModule {
    @Provides
    fun provideUserRepositoryImpl(
        storage: UserStorageInterface
    ) : UserRepositoryInterface {
        return UserRepositoryImpl(storage)
    }

    @Provides
    fun provideSignInUseCase(
        repository: UserRepositoryInterface
    ) = SignInUseCase(repository)

    @Provides
    fun provideSignUpUseCase(
        repository: UserRepositoryInterface
    ) = SignUpUseCase(repository)
}