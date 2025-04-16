package com.shipsmartapp.delivery_choosing.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.data.PackageExtraParams
import com.shipsmartapp.delivery_choosing.data.network.NetworkResponse
import com.shipsmartapp.delivery_choosing.domain.usecases.GetDeliveryCostUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeliveryChooserViewModel: ViewModel() {
    @Inject
    lateinit var getDeliveryCostUseCase: GetDeliveryCostUseCase

    private val _deliveryCost = MutableSharedFlow<NetworkResponse>()
    val deliveryCost: SharedFlow<NetworkResponse> = _deliveryCost

    fun getDeliveryCost(packageParams: PackageExtraParams) {
        viewModelScope.launch(Dispatchers.IO) {
            val networkResponse = getDeliveryCostUseCase.execute(packageParams)
            _deliveryCost.emit(networkResponse)
        }
    }
}