package com.shipsmartapp.login.domain.repository

import com.core.db_network.data.storage.RegistrationParams
import com.shipsmartapp.login.data.states.UserDataState

interface UserRepositoryInterface {
    suspend fun getUser(regParams: RegistrationParams): UserDataState
    suspend fun saveUser(regParams: RegistrationParams): Boolean
}