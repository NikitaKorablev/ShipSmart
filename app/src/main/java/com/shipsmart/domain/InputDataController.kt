package com.shipsmart.domain

import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.shipsmart.data.dbLogic.SupabaseDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InputDataController(activity: AppCompatActivity) {
    private val lifecycleScope: LifecycleCoroutineScope = activity.lifecycleScope
    private var authorisationDB = SupabaseDAO()
    private val toastConstructor = ToastConstructor(activity)

    lateinit var emailInput : EditText
    lateinit var passwordInput : EditText

    fun login(view: View?) {
        Log.e(TEST, "login was called")

        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()

        if (!dataIsValid(email, password)) return
        checkUserPassword(email, password)
    }

    private fun dataIsValid(email: String, password: String) : Boolean {
        if (!emailIsValid(email)) {
            toastConstructor.show("Email is incorrect")
            return false
        }

        if (passwordIsValid(password)) {
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
        return password.length > 4
    }

    private fun checkUserPassword(email: String, password: String) {
        lifecycleScope.launch {
            try {
                val user = withContext(Dispatchers.IO) {
                    return@withContext authorisationDB.getUser(email = email)
                }

                if (user == null) {
                    toastConstructor.show("User with this email is undefined!")
                    return@launch
                }
                if (user.password != password) {
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

    companion object {
        const val TAG = "InputDataController"
        const val ERROR = "InputDataController.error"
        const val TEST = "InputDataControllerTest"
    }
}