package com.core.delivery_network.data.companies

import com.startsmartapp.design_system.R
import com.core.delivery_network.data.PackageParams

class Boxberry: DeliveryCompany {
    override val name = "Boxberry"
    override val imgResource = R.drawable.boxberry

    private val cityUrl = "https://boxberry.ru/api/v1/cities/list"
    private val costUrl = "https://boxberry.ru/proxy/delivery/cost/pip?method=TarificationLaP"

    override fun getCityUrl(city: String): String { return cityUrl }

    override fun getCostUrl(packageExtraParams: PackageParams): String {
        val cityParams = packageExtraParams.cityParams
        return "$costUrl&sender_city=${cityParams.senderCity}" +
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