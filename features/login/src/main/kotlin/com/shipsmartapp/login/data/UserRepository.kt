package com.shipsmartapp.login.data

import android.util.Log
import com.core.db_network.domain.UserStorageInterface
import com.core.db_network.data.storage.SupabaseUser
import com.core.db_network.data.storage.RegistrationParams
import com.shipsmartapp.login.data.states.UserDataState
import com.shipsmartapp.login.domain.repository.UserRepositoryInterface
import java.io.IOException

class UserRepositoryImpl(private var userStorage: com.core.db_network.domain.UserStorageInterface) : UserRepositoryInterface {
    override suspend fun getUser(regParams: com.core.db_network.data.storage.RegistrationParams): UserDataState {
        return try {
            val user = userStorage.getUser(email = regParams.email)
            UserDataState.AcceptState(mapToDomain(user))
        } catch (error: Exception) {
            val message = "Exception of getting regParams data: ${error.message}"
            UserDataState.ErrorState(message)
        }
    }

    override suspend fun saveUser(regParams: com.core.db_network.data.storage.RegistrationParams): Boolean {
        val storageUser = mapToStorage(params=regParams)

        try {
            return userStorage.addUser(storageUser)
        } catch (e: IOException) {
            Log.e(TAG, "Error adding regParams: " + e.message)
            return false
        }
    }

    private fun mapToStorage(params: com.core.db_network.data.storage.RegistrationParams): com.core.db_network.data.storage.SupabaseUser {
        return com.core.db_network.data.storage.SupabaseUser(
            params.id,
            params.name,
            params.email,
            params.password
        )
    }

    private fun mapToDomain(supabaseUser: com.core.db_network.data.storage.SupabaseUser?): com.core.db_network.data.storage.RegistrationParams? {
        if (supabaseUser == null) return null
        return com.core.db_network.data.storage.RegistrationParams(
            supabaseUser.id,
            supabaseUser.name,
            supabaseUser.email,
            supabaseUser.password
        )
    }

    companion object {
        const val TAG = "UserRepository"
        const val TEST = "UserRepositoryTest"
    }
}