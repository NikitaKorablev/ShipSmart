package com.core.delivery_network.data.companies

import com.startsmartapp.design_system.R
import com.core.delivery_network.data.PackageParams
import okio.IOException

class CDEK: DeliveryCompany {
    override val name = "CDEK"
    override val imgResource = R.drawable.cdek

    private val url = "https://cdek.ru/api-lkfl"

    override fun getCityUrl(city: String): String {
        if (city.isEmpty()) throw IOException("City name is undefined")

        return "$url/cities/autocomplete/?str=$city&limit=1"
    }

    override fun getCostUrl(packageExtraParams: PackageParams): String {
        return "$url/estimateV2/"
    }
}