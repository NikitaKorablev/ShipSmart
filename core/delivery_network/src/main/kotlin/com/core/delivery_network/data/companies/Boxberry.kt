package com.core.delivery_network.data.companies

import com.startsmartapp.design_system.R
import com.core.delivery_network.data.PackageExtraParams

class Boxberry: DeliveryCompany {
    override val name = "Boxberry"
    override val url = "https://boxberry.ru/proxy/delivery/cost/pip?method=TarificationLaP"
    override val imgResource = R.drawable.boxberry

    override fun getCityUrl(): String {
        return "https://boxberry.ru/api/v1/cities/list"
    }
    override fun getCostUrl(packageExtraParams: PackageExtraParams): String {
        val cityParams = packageExtraParams.cityParams
        return "$url&sender_city=${cityParams.senderCity}" +
                "&sender_country=${cityParams.senderCountry}" +
                "&receiver_city=${cityParams.receiverCity}" +
                "&receiver_country=${cityParams.receiverCountry}" +
                "&public_price=100000" +
                "&package[boxberry_package]=0" +
                "&package[width]=${packageExtraParams.width}" +
                "&package[height]=${packageExtraParams.height}" +
                "&package[depth]=${packageExtraParams.length}"
    }
}