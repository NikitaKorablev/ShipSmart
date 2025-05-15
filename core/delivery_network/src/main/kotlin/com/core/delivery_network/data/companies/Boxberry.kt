package com.core.delivery_network.data.companies

import com.startsmartapp.design_system.R
import com.core.delivery_network.data.PackageExtraParams

class Boxberry: DeliveryCompany {
    override val name = "Boxberry"
    override val url = "https://boxberry.ru/proxy/delivery/cost/pip?method=TarificationLaP"
    override val imgResource = R.drawable.boxberry

    override fun getUrl(packageExtraParams: PackageExtraParams): String {
        return "$url&sender_city=68&sender_country=643" +
                "&receiver_city=33&receiver_country=643" +
                "&public_price=100000" +
                "&package[boxberry_package]=0" +
                "&package[width]=${packageExtraParams.width}" +
                "&package[height]=${packageExtraParams.height}" +
                "&package[depth]=${packageExtraParams.length}"
    }
}