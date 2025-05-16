package com.shipsmartapp.package_size_collector.utils

import android.content.Context
import com.core.delivery_network.data.PackageData
import com.core.utils.Router
import com.core.delivery_network.data.PackageExtraParams

interface PackageParamsCollectionRouter: Router {
    fun navToDeliveryChoosingActivity(context: Context, packageData: PackageData)
}