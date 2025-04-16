package com.shipsmartapp.delivery_choosing.domain.usecases

import com.core.data.PackageExtraParams
import com.shipsmartapp.delivery_choosing.data.network.NetworkResponse
import com.shipsmartapp.delivery_choosing.domain.repository.DeliveryRepository

class GetDeliveryCostUseCase(
    private val deliveryRep: DeliveryRepository
) {
    suspend fun execute(packageParams: PackageExtraParams): NetworkResponse {
        val url = getUrl(packageParams)
        return deliveryRep.getDeliveryCost(url)
    }

    private fun getUrl(packageParams: PackageExtraParams): String {
        var url = "https://boxberry.ru/proxy/delivery/cost/pip?method=TarificationLaP"
        url += "&sender_city=68&sender_country=643"
        url += "&receiver_city=33&receiver_country=643"
        url += "&public_price=100000"
        url += "&package[boxberry_package]=0"
        url += "&package[width]=${packageParams.width}"
        url += "&package[height]=${packageParams.height}"
        url += "&package[depth]=${packageParams.length}"

        return url
    }
}