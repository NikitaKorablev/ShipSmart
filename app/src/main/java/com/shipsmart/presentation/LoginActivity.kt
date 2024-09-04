package com.shipsmart.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.shipsmart.R
import com.shipsmart.data.UserRepository
import com.shipsmart.data.storage.SupabaseUserStorage
import com.shipsmart.domain.model.SignActivityObjects
import com.shipsmart.domain.repository.ToastConstructor
import com.shipsmart.domain.usecases.SignInUseCase
import com.shipsmart.domain.usecases.SignUpUseCase
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private val userRepository = UserRepository(SupabaseUserStorage())

    private val signInUseCase = SignInUseCase(userRepository = userRepository)
    private val signUpUseCase = SignUpUseCase(userRepository = userRepository)

    private lateinit var signActivityObjects: SignActivityObjects
    private lateinit var toast: ToastConstructor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        toast = ToastConstructor(context = this)

        val activityLabel = findViewById<TextView>(R.id.activityLabel)
        val enterButton = findViewById<Button>(R.id.enter_button)
        val changeActivityButton = findViewById<TextView>(R.id.change_activity)

        this.signActivityObjects = SignActivityObjects(activityLabel,
            enterButton, changeActivityButton)

        setLoginActivity()

        Log.d(TEST, "login_window start")
    }

    private fun setLoginActivity() {
        signActivityObjects.activityLabel.text = "Вход"
        signActivityObjects.enterButton.text = "Войти"

        signActivityObjects.enterButton.setOnClickListener {
            val email: String = findViewById<TextView>(R.id.EmailAddress).text.toString()
            val password: String = findViewById<TextView>(R.id.Password).text.toString()

            AppCompatActivity().lifecycleScope.launch {
                if (signInUseCase.execute(email, password))
                    toast.show("Welcome back!")
                else
                    toast.show("Email or password is incorrect!")
            }
        }

        signActivityObjects.changeActivityButton.text = "Создать аккаунт"
        signActivityObjects.changeActivityButton.setOnClickListener{ setSignupActivity() }
    }

    private fun setSignupActivity() {
        signActivityObjects.activityLabel.text = "Регистрация"

        signActivityObjects.enterButton.text = "Зарегистрироваться"
        signActivityObjects.enterButton.setOnClickListener {
            val email: String = findViewById<TextView>(R.id.EmailAddress).text.toString()
            val password: String = findViewById<TextView>(R.id.Password).text.toString()

            AppCompatActivity().lifecycleScope.launch {
                if (signUpUseCase.execute(email, password))
                    toast.show("Welcome!")
                else
                    toast.show("Email or password is incorrect!")
            }
        }

        signActivityObjects.changeActivityButton.text = "Уже есть аккаунт"
        signActivityObjects.changeActivityButton.setOnClickListener{ setLoginActivity() }
    }

    companion object {
        const val TAG = "Main_Activity"
        const val LOGIN_WINDOW = "LogIn_Window"
        const val TEST = "LoginActivityTest"
    }
}