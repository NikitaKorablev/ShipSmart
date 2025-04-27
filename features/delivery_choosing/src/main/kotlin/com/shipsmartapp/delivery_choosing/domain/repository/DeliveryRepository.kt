package com.shipsmartapp.delivery_choosing.domain.repository

import com.core.delivery_network.data.PackageExtraParams
import com.shipsmartapp.delivery_choosing.data.DeliveryCompany
import com.shipsmartapp.delivery_choosing.data.network.NetworkResponse

interface DeliveryRepository {
    suspend fun getDeliveryCost(
        company: DeliveryCompany,
        packageParams: PackageExtraParams
    ): NetworkResponse
}