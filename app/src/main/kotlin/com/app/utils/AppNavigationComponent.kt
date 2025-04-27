package com.app.utils

import android.content.Context
import android.content.Intent
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
        packageParams: PackageExtraParams
    ) {
        val intent = Intent(context, DeliveryChooserActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)

        intent.putExtra("height", packageParams.height)
        intent.putExtra("width", packageParams.width)
        intent.putExtra("length", packageParams.length)
        intent.putExtra("from", packageParams.from)
        intent.putExtra("where", packageParams.where)

        context.startActivity(intent)
    }
}