package com.shipsmartapp.package_size_collector.utils

import android.content.Context
import com.core.delivery_network.data.PackageParams
import com.core.utils.Router

interface PackageParamsCollectionRouter: Router {
    fun navToDeliveryChoosingActivity(context: Context, packageData: PackageParams)
}