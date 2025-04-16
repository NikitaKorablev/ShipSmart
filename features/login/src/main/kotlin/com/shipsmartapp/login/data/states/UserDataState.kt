package com.shipsmartapp.login.data.states

import com.core.data.storage.RegistrationParams

sealed class UserDataState {
    data class AcceptState(val regParams: RegistrationParams?): UserDataState()
    data class ErrorState(val message: String): UserDataState()
}