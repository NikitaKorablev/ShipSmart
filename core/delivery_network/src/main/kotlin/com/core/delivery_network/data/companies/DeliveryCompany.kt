package com.core.delivery_network.data.companies

import com.core.delivery_network.data.PackageExtraParams

interface DeliveryCompany {
    val name: String
    val url: String

    fun getUrl(packageExtraParams: PackageExtraParams): String
}