package com.core.delivery_network.data.companies

import com.core.delivery_network.data.PackageExtraParams

class CDEK: DeliveryCompany {
    override val name: String
        get() = "CDEK"
    override val url: String
        get() = "https://www.cdek.ru/api-lkfl/getTariffInfo/"

    override fun getUrl(packageExtraParams: PackageExtraParams): String {
        return url
    }

}