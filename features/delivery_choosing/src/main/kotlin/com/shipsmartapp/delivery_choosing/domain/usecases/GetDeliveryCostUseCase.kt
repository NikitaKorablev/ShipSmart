package com.shipsmartapp.delivery_choosing.domain.usecases

import com.core.delivery_network.data.PackageExtraParams
import com.core.delivery_network.data.companies_repos.DeliveryResponse
import com.core.delivery_network.domain.repository.DeliveryReposList
import com.core.delivery_network.data.PackageData
import com.core.delivery_network.data.companies_repos.city_response_data.CityResponse
import com.core.delivery_network.domain.repository.DeliveryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

class GetDeliveryCostUseCase @Inject constructor(
    private val repositories: DeliveryReposList
) {
    fun execute(packageParams: PackageData): Flow<DeliveryResponse> = channelFlow {
        supervisorScope {
            val jobs = repositories.list.map { repo ->
                launch { send(calculateCost(repo, packageParams)) }
            }
            jobs.joinAll()
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun calculateCost(
        repo: DeliveryRepository,
        packageData: PackageData,
    ): DeliveryResponse {

        return when (val cityResponse = repo.getCitiesData(packageData)) {
            is CityResponse.Error ->
                DeliveryResponse.Error(cityResponse.message)

            is CityResponse.Accept -> {
                val packageParams = PackageExtraParams(
                    height = packageData.height.toInt(),
                    width = packageData.width.toInt(),
                    length = packageData.length.toInt(),

                    cityParams = cityResponse.data
                )
                repo.getDeliveryCost(packageParams)
            }

        }
    }
}