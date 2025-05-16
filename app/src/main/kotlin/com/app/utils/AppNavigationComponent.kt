package com.app.utils

import android.content.Context
import android.content.Intent
import com.core.delivery_network.data.PackageData
import com.core.delivery_network.data.PackageExtraParams
import com.shipsmartapp.delivery_choosing.presentation.DeliveryChooserActivity
import com.shipsmartapp.package_size_collector.utils.PackageParamsCollectionRouter
import com.shipsmartapp.package_size_collector.presentation.PackageParamsActivity
import com.shipsmartapp.login.utils.LoginRouter

class AppNavigationComponent: LoginRouter, PackageParamsCollectionRouter {
    override fun navToMainActivity(context: Context) {
        val intent = Intent(context, PackageParamsActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        context.startActivity(intent)
    }

    override fun navToDeliveryChoosingActivity(
        context: Context,
        packageData: PackageData
    ) {
        val intent = Intent(context, DeliveryChooserActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)

        intent.putExtra("height", packageData.height)
        intent.putExtra("width", packageData.width)
        intent.putExtra("length", packageData.length)
        intent.putExtra("sender_city", packageData.cityData.senderCity)
        intent.putExtra("sender_country", packageData.cityData.senderCountry)
        intent.putExtra("receiver_city", packageData.cityData.receiverCity)
        intent.putExtra("receiver_country", packageData.cityData.receiverCountry)

        context.startActivity(intent)
    }
}