package com.shipsmart.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.shipsmart.R
import com.shipsmart.data.dbLogic.SupabaseDAO
import com.shipsmart.domain.repository.InputDataController

class LoginActivity : AppCompatActivity() {
    private lateinit var inputDataController : InputDataController
    private lateinit var activityLabel: TextView
    private lateinit var enterButton: Button
    private lateinit var changeActivityButton: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        inputDataController = InputDataController(this)
        inputDataController.dbDao = SupabaseDAO()

        Log.d(TEST, "login_window start")

        this.activityLabel = findViewById(R.id.activityLabel)
        this.enterButton = findViewById(R.id.enter_button)
        this.changeActivityButton = findViewById(R.id.change_activity)

        inputDataController.emailInput = findViewById(R.id.EmailAddress)
        inputDataController.passwordInput = findViewById(R.id.Password)

        this.enterButton.setOnClickListener(inputDataController::login)
        this.changeActivityButton.setOnClickListener(this::setSignupActivity)
    }

    private fun setLoginActivity(view: View?) {
        activityLabel.text = "Вход"

        enterButton.text = "Войти"
        enterButton.setOnClickListener(inputDataController::login)

        changeActivityButton.text = "Создать аккаунт"
        changeActivityButton.setOnClickListener(this::setSignupActivity)
    }

    private fun setSignupActivity(view: View?) {
        activityLabel.text = "Регистрация"

        enterButton.text = "Зарегистрироваться"
        enterButton.setOnClickListener(inputDataController::signup)

        changeActivityButton.text = "Уже есть аккаунт"
        changeActivityButton.setOnClickListener(this::setLoginActivity)
    }

    companion object {
        const val TAG = "Main_Activity"
        const val LOGIN_WINDOW = "LogIn_Window"
        const val TEST = "LoginActivityTest"
    }
}