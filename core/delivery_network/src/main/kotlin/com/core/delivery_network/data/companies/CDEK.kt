package com.core.delivery_network.data.companies

import com.startsmartapp.design_system.R
import com.core.delivery_network.data.PackageExtraParams

class CDEK: DeliveryCompany {
    override val name = "CDEK"
    override val url = "https://www.cdek.ru/api-lkfl/getTariffInfo/"
    override val imgResource = R.drawable.cdek

    override fun getCityUrl(): String {
        TODO("Not yet implemented")
    }
    override fun getCostUrl(packageExtraParams: PackageExtraParams): String {
        return url
    }
}