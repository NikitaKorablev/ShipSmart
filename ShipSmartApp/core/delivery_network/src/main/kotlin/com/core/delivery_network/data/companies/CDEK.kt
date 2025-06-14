package com.core.delivery_network.data.companies

import com.startsmartapp.design_system.R
import com.core.delivery_network.data.PackageParams
import okio.IOException

class CDEK: DeliveryCompany {
    override val name = "CDEK"
    override val imgResource = R.drawable.cdek

    private val url = "https://www.cdek.ru/api-lkfl"
    private val costUrl = "http://147.45.246.48:8100/get_delivery_cost"

    override fun getCityUrl(city: String): String {
        if (city.isEmpty()) throw IOException("City name is undefined")

        return "$url/cities/autocomplete/?str=$city&limit=1"
    }

    override fun getCostUrl(packageExtraParams: PackageParams): String {
        val city = packageExtraParams.cityParams

        return "$costUrl?senderCityId=${city.senderCity}" +
                "&receiverCityId=${city.receiverCity}" +
                "&length=${packageExtraParams.length.toInt()}" +
                "&width=${packageExtraParams.width.toInt()}" +
                "&height=${packageExtraParams.height.toInt()}" +
                "&weight=10"


    }
}