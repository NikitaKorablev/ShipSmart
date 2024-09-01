package com.shipsmart.data

import android.util.Log
import com.shipsmart.domain.model.RegistrationParams
import com.shipsmart.domain.model.SupabaseUser
import com.shipsmart.domain.repository.DBdao
import com.shipsmart.domain.repository.UserRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class UserRepository(private var dbDao: DBdao) : UserRepositoryInterface {

    override suspend fun getUser(regParams: RegistrationParams): SupabaseUser? {
        return withContext(Dispatchers.IO) {
            return@withContext dbDao.getUser(email = regParams.email)
        }
    }

    override suspend fun createNewUser(regParams: RegistrationParams): SupabaseUser? {
        val user = SupabaseUser(email=regParams.email, password=regParams.password)

        return withContext(Dispatchers.IO) {
            val resultUser : SupabaseUser?
            try {
                resultUser = dbDao.addUser(user)
            } catch (e: IOException) {
                Log.e(TAG, "Error adding user: ", e)
                return@withContext null
            }

            return@withContext resultUser
        }
    }

    companion object {
        const val TAG = "UserRepository"
        const val TEST = "UserRepositoryTest"
    }
}