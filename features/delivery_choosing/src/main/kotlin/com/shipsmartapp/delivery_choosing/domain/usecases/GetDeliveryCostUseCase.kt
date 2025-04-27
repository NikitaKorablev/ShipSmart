package com.shipsmartapp.delivery_choosing.domain.usecases

import com.core.delivery_network.data.PackageExtraParams
import com.shipsmartapp.delivery_choosing.data.DeliveryCompany
import com.shipsmartapp.delivery_choosing.data.network.NetworkResponse
import com.shipsmartapp.delivery_choosing.domain.repository.DeliveryRepository

class GetDeliveryCostUseCase(
    private val deliveryRep: DeliveryRepository
) {
    suspend fun execute(company: DeliveryCompany, packageParams: PackageExtraParams): NetworkResponse {
        return deliveryRep.getDeliveryCost(company, packageParams)
    }
}