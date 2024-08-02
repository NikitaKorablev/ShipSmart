package com.shipsmart.domain

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.shipsmart.R
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

        lifecycleScope.launch {
            val user = withContext(Dispatchers.IO) {
                return@withContext authorisationDB.getUser(email = email)
            }
            Log.e(TEST, "user is $user")
            if (user == null) toastConstructor.show("This user undefined!")
            else if (user.password != password) {
                toastConstructor.show("Your password is incorrect!")
            } else {
                toastConstructor.show("Welcome back!")
                // TODO: Start MainActivity
            }
        }
    }

    companion object {
        const val TAG = "InputDataController"
        const val TEST = "InputDataControllerTest"
    }
}