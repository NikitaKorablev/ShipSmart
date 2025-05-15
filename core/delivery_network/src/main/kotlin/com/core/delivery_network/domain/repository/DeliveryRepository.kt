package com.core.delivery_network.domain.repository

import com.core.delivery_network.data.PackageExtraParams
import com.core.delivery_network.data.companies.DeliveryCompany
import com.core.delivery_network.data.companies_repos.BoxberryDeliveryRepositoryImpl
import com.core.delivery_network.data.companies_repos.NetworkResponse
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

    suspend fun getDeliveryCost(
        packageParams: PackageExtraParams
    ): NetworkResponse
}