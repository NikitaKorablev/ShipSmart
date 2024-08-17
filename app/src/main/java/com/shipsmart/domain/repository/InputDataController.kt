package com.shipsmart.domain.repository

import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.shipsmart.domain.model.SupabaseUser
import com.shipsmart.domain.model.RegistrationParams
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InputDataController(activity: AppCompatActivity) {
    private val lifecycleScope: LifecycleCoroutineScope = activity.lifecycleScope
    private val toastConstructor = ToastConstructor(activity)

    lateinit var emailInput : EditText
    lateinit var passwordInput : EditText

    lateinit var dbDao: DBdao

    fun login(view: View?) {
        Log.d(TEST, "login was called")

        val regParams = RegistrationParams(emailInput.text.toString(), passwordInput.text.toString())

        if (!inputDataIsValid(regParams)) return
        checkUserPassword(regParams)
    }

    fun signup(view: View?) {
        Log.d(TEST, "signup was called")

        val regParams = RegistrationParams(emailInput.text.toString(), passwordInput.text.toString())

        if (!inputDataIsValid(regParams)) return
        createNewUser(regParams)
    }

    private fun inputDataIsValid(params: RegistrationParams) : Boolean {
        if (!emailIsValid(params.email)) {
            toastConstructor.show("Email is incorrect")
            return false
        }

        if (!passwordIsValid(params.password)) {
            toastConstructor.show("Password is incorrect")
            return false
        }

        return true
    }

    private fun emailIsValid(email: String): Boolean {
        if (email.isBlank() || email.isEmpty())
            return false
        if (!email.contains("@") || !email.contains("."))
            return false
        return true
    }

    private fun passwordIsValid(password: String): Boolean {
        //TODO: add password validation
        return password.length > 3
    }

    private fun checkUserPassword(regParams: RegistrationParams) {
        lifecycleScope.launch {
            try {
                val user = withContext(Dispatchers.IO) {
                    return@withContext dbDao.getUser(email = regParams.email)
                }

                if (user == null) {
                    toastConstructor.show("User with this email is undefined!")
                    return@launch
                }
                if (user.password != regParams.password) {
                    toastConstructor.show("Your password is incorrect!")
                    return@launch
                }

                // TODO: Start MainActivity
                toastConstructor.show("Welcome back!")
            } catch (e: Exception) {
                Log.e(ERROR, e.message.toString())
                toastConstructor.show("An error occurred!")
            }
        }
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
        const val TAG = "InputDataController"
        const val ERROR = "InputDataController.error"
        const val TEST = "InputDataControllerTest"
    }
}