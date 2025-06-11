package com.core.delivery_network.data.companies

import com.core.delivery_network.data.PackageParams

interface DeliveryCompany {
    val name: String
    val imgResource: Int

    fun getCityUrl(city: String = ""): String
    fun getCostUrl(packageExtraParams: PackageParams): String
}