package com.shipsmartapp.package_size_collector.utils

import android.content.Context
import com.core.utils.Router
import com.core.data.PackageExtraParams

interface PackageParamsCollectionRouter: Router {
    fun navToDeliveryChoosingActivity(context: Context, packageParams: PackageExtraParams)
}