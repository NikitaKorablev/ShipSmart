package com.app.domain.repository

import com.app.domain.model.RegistrationParams

interface UserRepositoryInterface {
    suspend fun getUser(regParams: RegistrationParams): RegistrationParams?
    suspend fun saveUser(regParams: RegistrationParams): Boolean
}