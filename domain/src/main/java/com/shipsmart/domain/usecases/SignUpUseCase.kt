package com.shipsmart.domain.usecases

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.shipsmart.domain.model.RegistrationParams
import com.shipsmart.domain.model.SupabaseUser
import com.shipsmart.domain.repository.DBdao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class SignInUseCase(activity: AppCompatActivity): InputDataChecker(activity) {
    lateinit var dbDao: DBdao

    fun signIn(email: String, password: String) {
        Log.d(TEST, "signup was called")

        val regParams = RegistrationParams(email, password)

        if (!inputDataIsValid(regParams)) return
        createNewUser(regParams)
    }

    private fun createNewUser(regParams: RegistrationParams) {
        lifecycleScope.launch {
            val user = SupabaseUser(email=regParams.email, password=regParams.password)

            try {
                withContext(Dispatchers.IO) {
                    try {
                        dbDao.addUser(user)
                        toastConstructor.show("Successfully created user")
                    } catch (e: IOException) {
                        toastConstructor.show(e.message.toString())
                    } catch(e: Exception) {
                        throw e
                    }
                }
            } catch (e: Exception) {
                Log.e(ERROR, e.message.toString())
                toastConstructor.show(e.message.toString())
            }
        }
    }

    companion object {
        const val TAG = "SignInUseCase"
        const val ERROR = "SignInUseCase.error"
        const val TEST = "SignInUseCaseTest"
    }
}