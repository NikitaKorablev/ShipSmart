package com.shipsmartapp.delivery_choosing.domain.usecases

import android.util.Log
import com.core.delivery_network.data.response_data.DeliveryResponse
import com.core.delivery_network.domain.repository.DeliveryReposList
import com.core.delivery_network.data.PackageParams
import com.core.delivery_network.data.response_data.city_response_data.CityResponse
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
    fun execute(packageParams: PackageParams): Flow<DeliveryResponse> = channelFlow {
        supervisorScope {
            val jobs = repositories.list.map { repo ->
                launch {
                    try {
                        send(calculateCost(repo, packageParams))
                    } catch (err: Exception) {
                        Log.d(TAG, "${repo.company.name} exception")
                    }
                }
            }
            jobs.joinAll()
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun calculateCost(
        repo: DeliveryRepository,
        packageData: PackageParams,
    ): DeliveryResponse {

        return when (val cityResponse = repo.getCitiesData(packageData)) {
            is CityResponse.Error ->
                DeliveryResponse.Error(cityResponse.message)

            is CityResponse.Accept -> {
                val packageParams = PackageParams(
                    height = packageData.height,
                    width = packageData.width,
                    length = packageData.length,

                    cityParams = cityResponse.data
                )
                repo.getDeliveryCost(packageParams)
            }
        }
    }

    companion object {
        const val TAG = "DeliveryCostUseCase"
    }
}