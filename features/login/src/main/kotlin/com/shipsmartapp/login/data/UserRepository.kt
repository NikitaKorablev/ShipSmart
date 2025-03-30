package com.shipsmartapp.login.data

import android.util.Log
import com.core.domain.UserStorageInterface
import com.core.data.SupabaseUser
import com.core.data.RegistrationParams
import com.shipsmartapp.login.data.states.UserDataState
import com.shipsmartapp.login.domain.repository.UserRepositoryInterface
import java.io.IOException

class UserRepositoryImpl(private var userStorage: UserStorageInterface) : UserRepositoryInterface {
    override suspend fun getUser(regParams: RegistrationParams): UserDataState {
        return try {
            val user = userStorage.getUser(email = regParams.email)
            UserDataState.AcceptState(mapToDomain(user))
        } catch (error: Exception) {
            val message = "Exception of getting regParams data: ${error.message}"
            UserDataState.ErrorState(message)
        }
    }

    override suspend fun saveUser(regParams: RegistrationParams): Boolean {
        val storageUser = mapToStorage(params=regParams)

        try {
            return userStorage.addUser(storageUser)
        } catch (e: IOException) {
            Log.e(TAG, "Error adding regParams: " + e.message)
            return false
        }
    }

    private fun mapToStorage(params: RegistrationParams): SupabaseUser {
        return SupabaseUser(params.id, params.name, params.email, params.password)
    }

    private fun mapToDomain(supabaseUser: SupabaseUser?): RegistrationParams? {
        if (supabaseUser == null) return null
        return RegistrationParams(supabaseUser.id, supabaseUser.name, supabaseUser.email, supabaseUser.password)
    }

    companion object {
        const val TAG = "UserRepository"
        const val TEST = "UserRepositoryTest"
    }
}