package com.shipsmart.domain.repository

import com.shipsmart.domain.model.RegistrationParams

interface UserRepositoryInterface {
    suspend fun getUser(regParams: RegistrationParams): RegistrationParams?
    suspend fun saveUser(regParams: RegistrationParams): Boolean
}