package com.shipsmartapp.login.di

import com.core.utils.Router
import com.shipsmartapp.login.presentation.LoginActivity
import com.shipsmartapp.login.presentation.viewmodel.LoginViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [LoginModule::class], dependencies = [LoginDeps::class])
interface LoginComponent {
    fun inject(loginActivity: LoginActivity)
    fun inject(viewModel: LoginViewModel)

    @Component.Builder
    interface Builder {
        fun deps(deps: LoginDeps): Builder
        fun build(): LoginComponent
    }
}

interface LoginDeps {
    val router: Router
}

interface LoginDepsProvider {
    fun getLoginComponent(): LoginComponent
}