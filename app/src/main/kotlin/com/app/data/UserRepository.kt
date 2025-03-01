package com.app.data

import android.util.Log
import com.app.data.storage.UserStorageInterface
import com.app.data.storage.model.SupabaseUser
import com.app.domain.model.RegistrationParams
import com.app.domain.repository.UserRepositoryInterface
import java.io.IOException

class UserRepository(private var userStorage: UserStorageInterface) : UserRepositoryInterface {
    override suspend fun getUser(regParams: RegistrationParams): RegistrationParams? {
        val res = userStorage.getUser(email = regParams.email)
        return mapToDomain(res)
    }

    override suspend fun saveUser(regParams: RegistrationParams): Boolean {
        val storageUser = mapToStorage(params=regParams)

        try {
            return userStorage.addUser(storageUser)
        } catch (e: IOException) {
            Log.e(TAG, "Error adding user: " + e.message)
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