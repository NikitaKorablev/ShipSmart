package com.shipsmartapp.login.utils

import android.content.Context
import com.core.utils.Router

interface LoginRouter: Router {
    fun navToMainActivity(context: Context)
}