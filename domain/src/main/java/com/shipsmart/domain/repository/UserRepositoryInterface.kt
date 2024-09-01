package com.shipsmart.domain.repository

import com.shipsmart.domain.model.RegistrationParams
import com.shipsmart.domain.model.SupabaseUser

interface UserRepositoryInterface {
    suspend fun getUser(regParams: RegistrationParams): SupabaseUser?
    suspend fun createNewUser(regParams: RegistrationParams): SupabaseUser?
}