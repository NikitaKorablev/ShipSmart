package com.shipsmartapp.package_size_collector.di

import com.core.utils.Router
import com.shipsmartapp.package_size_collector.presentation.PackageParamsActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [PackageCollectorDeps::class]
)
interface PackageCollectorComponent {
    fun inject(loginActivity: PackageParamsActivity)

    @Component.Builder
    interface Builder {
        fun deps(deps: PackageCollectorDeps): Builder
        fun build(): PackageCollectorComponent
    }
}

interface PackageCollectorDeps {
    val router: com.core.utils.Router
}

interface PackageCollectorDepsProvider {
    fun getPackageCollectorComponent(): PackageCollectorComponent
}