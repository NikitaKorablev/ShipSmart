package com.core.delivery_network.domain.repository

import com.core.delivery_network.data.PackageData
import com.core.delivery_network.data.PackageExtraParams
import com.core.delivery_network.data.companies.DeliveryCompany
import com.core.delivery_network.data.companies_repos.BoxberryDeliveryRepositoryImpl
import com.core.delivery_network.data.companies_repos.city_response_data.CityResponse
import com.core.delivery_network.data.companies_repos.DeliveryResponse
import javax.inject.Inject

class DeliveryReposList @Inject constructor(
    private val boxberry: BoxberryDeliveryRepositoryImpl,
//    private val cdek: CDEKDeliveryRepositoryImpl
) {

    val list: List<DeliveryRepository>
        get() {
            return listOf(boxberry)
        }
}

interface DeliveryRepository {
    val company: DeliveryCompany

    suspend fun getCitiesData(
        cityParams: PackageData
    ): CityResponse

    suspend fun getDeliveryCost(
        packageParams: PackageExtraParams,
    ): DeliveryResponse
}