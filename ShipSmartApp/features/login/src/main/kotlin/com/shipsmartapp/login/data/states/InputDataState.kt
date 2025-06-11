package com.shipsmartapp.login.data.states

sealed class InputDataState {
    data class AcceptState(val email: String) : InputDataState()
    data class ErrorState(val message: String) : InputDataState()
}