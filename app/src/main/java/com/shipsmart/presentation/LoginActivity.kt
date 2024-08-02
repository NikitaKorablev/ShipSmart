package com.shipsmart.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.shipsmart.R
import com.shipsmart.data.dbLogic.SupabaseDAO
import com.shipsmart.domain.InputDataController
import com.shipsmart.domain.ToastConstructor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var inputDataController : InputDataController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        inputDataController = InputDataController(this)

        Log.d(TEST, "login_window start")

        inputDataController.emailInput = findViewById(R.id.EmailAddress)
        inputDataController.passwordInput = findViewById(R.id.Password)

        val enterButton = findViewById<Button>(R.id.login_button)
        val openSignupButton = findViewById<TextView>(R.id.open_signUp)

        if (enterButton == null || openSignupButton == null) Log.e(LOGIN_WINDOW, "EnterButton is undefined.")
        else {
            enterButton.setOnClickListener(inputDataController::login)
            openSignupButton.setOnClickListener(this::onOpenSignupButtonClick)
        }
    }

    private fun onOpenSignupButtonClick(view: View?) {
        val intent = Intent(this, SignUpActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        startActivity(intent)
    }

    companion object {
        const val TAG = "Main_Activity"
        const val LOGIN_WINDOW = "LogIn_Window"
        const val TEST = "LoginActivityTest"

    }
}