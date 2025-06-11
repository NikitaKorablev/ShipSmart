package com.shipsmartapp.delivery_choosing.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.delivery_network.data.PackageParams
import com.core.delivery_network.data.response_data.DeliveryResponse
import com.shipsmartapp.delivery_choosing.domain.usecases.GetDeliveryCostUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeliveryChooserViewModel: ViewModel() {
    @Inject
    lateinit var getDeliveryCostUseCase: GetDeliveryCostUseCase

    private val _deliveryCost = MutableSharedFlow<DeliveryResponse>()
    val deliveryCost: SharedFlow<DeliveryResponse> = _deliveryCost

    fun getDeliveryCost(packageParams: PackageParams) {
        viewModelScope.launch(Dispatchers.IO) {
            getDeliveryCostUseCase.execute(packageParams).collect{ response ->
                _deliveryCost.emit(response)
            }
        }
    }
}