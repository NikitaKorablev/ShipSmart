package com.app.utils

import android.content.Context
import android.content.Intent
import com.app.presentation.PackageParamsActivity
import com.shipsmartapp.login.utils.LoginRouter

class AppNavigationComponent: LoginRouter {
    override fun navToMainActivity(context: Context) {
        val intent = Intent(context, PackageParamsActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        context.startActivity(intent)
    }
}