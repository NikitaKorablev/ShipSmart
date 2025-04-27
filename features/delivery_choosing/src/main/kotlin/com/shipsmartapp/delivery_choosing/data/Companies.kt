package com.shipsmartapp.delivery_choosing.data

import com.core.delivery_network.data.PackageExtraParams

interface DeliveryCompany {
    val name: String
    val url: String

    fun getUrl(packageExtraParams: com.core.delivery_network.data.PackageExtraParams): String
}

class Boxberry : DeliveryCompany {
    override val name: String
        get() = "Boxberry"
    override val url: String
        get() = "https://boxberry.ru/proxy/delivery/cost/pip?method=TarificationLaP"

    override fun getUrl(packageExtraParams: com.core.delivery_network.data.PackageExtraParams): String {
        return "$url&sender_city=68&sender_country=643" +
                "&receiver_city=33&receiver_country=643" +
                "&public_price=100000" +
                "&package[boxberry_package]=0" +
                "&package[width]=${packageExtraParams.width}" +
                "&package[height]=${packageExtraParams.height}" +
                "&package[depth]=${packageExtraParams.length}"
    }
}
