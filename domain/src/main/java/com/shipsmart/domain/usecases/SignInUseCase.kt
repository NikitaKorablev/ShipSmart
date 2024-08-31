package com.shipsmart.domain.usecases

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.shipsmart.domain.model.RegistrationParams
import com.shipsmart.domain.repository.DBdao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignIn_UseCase(activity: AppCompatActivity) : InputDataChecker(activity) {
    lateinit var dbDao: DBdao

    fun signUp(email: String, password: String) {
        Log.d(TEST, "login was called")

        val regParams = RegistrationParams(email, password)

        if (!inputDataIsValid(regParams)) return
        checkUserPassword(regParams)
    }

    private fun checkUserPassword(regParams: RegistrationParams) {
        lifecycleScope.launch {
            try {
                val user = withContext(Dispatchers.IO) {
                    return@withContext dbDao.getUser(email = regParams.email)
                }

                if (user == null) {
                    toastConstructor.show("User with this email is undefined!")
                    return@launch // уничтожается текущая корутина
                }
                if (user.password != regParams.password) {
                    toastConstructor.show("Your password is incorrect!")
                    return@launch // уничтожается текущая корутина
                }

                // TODO: Start MainActivity
                toastConstructor.show("Welcome back!")
            } catch (e: Exception) {
                Log.e(ERROR, e.message.toString())
                toastConstructor.show("An error occurred!")
            }
        }
    }

    companion object {
        const val TAG = "SignUpUseCase"
        const val ERROR = "SignUpUseCase.error"
        const val TEST = "SignUpUseCaseTest"
    }
}