package com.shipsmartapp.delivery_choosing.domain.usecases

import com.core.delivery_network.data.PackageExtraParams
import com.core.delivery_network.data.companies_repos.NetworkResponse
import com.core.delivery_network.domain.repository.DeliveryReposList
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
    fun execute(packageParams: PackageExtraParams): Flow<NetworkResponse> = channelFlow {
        supervisorScope {
            val jobs = repositories.list.map { repo ->
                launch {
                    val response = repo.getDeliveryCost(packageParams)
                    send(response)
                }
            }
            jobs.joinAll()
        }
    }.flowOn(Dispatchers.IO)
}