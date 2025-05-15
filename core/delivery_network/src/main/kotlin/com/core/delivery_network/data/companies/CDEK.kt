package com.core.delivery_network.data.companies

import com.startsmartapp.design_system.R
import com.core.delivery_network.data.PackageExtraParams

class CDEK: DeliveryCompany {
    override val name = "CDEK"
    override val url = "https://www.cdek.ru/api-lkfl/getTariffInfo/"
    override val imgResource = R.drawable.cdek

    override fun getUrl(packageExtraParams: PackageExtraParams): String {
        return url
    }

}