package com.core.delivery_network.domain.repository

import com.core.delivery_network.data.PackageParams
import com.core.delivery_network.data.companies.DeliveryCompany
import com.core.delivery_network.data.companies_repos.BoxberryDeliveryRepositoryImpl
import com.core.delivery_network.data.companies_repos.CDEKDeliveryRepositoryImpl
import com.core.delivery_network.data.response_data.city_response_data.CityResponse
import com.core.delivery_network.data.response_data.DeliveryResponse
import javax.inject.Inject

class DeliveryReposList @Inject constructor(
    private val boxberry: BoxberryDeliveryRepositoryImpl,
    private val cdek: CDEKDeliveryRepositoryImpl
) {

    val list: List<DeliveryRepository>
        get() {
            return listOf(boxberry, cdek)
        }
}

interface DeliveryRepository {
    val company: DeliveryCompany

    suspend fun getCitiesData(
        packageParams: PackageParams
    ): CityResponse

    suspend fun getDeliveryCost(
        packageParams: PackageParams,
    ): DeliveryResponse
}