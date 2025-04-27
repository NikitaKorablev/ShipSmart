package com.shipsmartapp.login.data.states

import com.core.db_network.data.storage.RegistrationParams

sealed class UserDataState {
    data class AcceptState(val regParams: com.core.db_network.data.storage.RegistrationParams?): UserDataState()
    data class ErrorState(val message: String): UserDataState()
}