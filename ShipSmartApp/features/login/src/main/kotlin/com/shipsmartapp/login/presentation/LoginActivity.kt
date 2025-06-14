package com.shipsmartapp.login.presentation

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.core.utils.Router
import com.shipsmartapp.login.R
import com.shipsmartapp.login.data.states.InputDataState
import com.shipsmartapp.login.databinding.ActivityLoginBinding
import com.shipsmartapp.login.di.LoginDepsProvider
import com.shipsmartapp.login.presentation.viewmodel.LoginViewModel
import com.shipsmartapp.login.utils.LoginRouter
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var router: Router
    private val viewModel: LoginViewModel by viewModels()

    private lateinit var loginSettings: SharedPreferences
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login_main_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, systemBars.bottom)
            insets
        }

        initDI()

        loginSettings = getSharedPreferences(LOGIN_SETTINGS, MODE_PRIVATE)
        setLoginActivity()

        lifecycleScope.launch {
            listenLoginResultMessage()
        }
    }

    private fun initDI() {
        val loginComponent =
            (applicationContext as LoginDepsProvider).getLoginComponent()
        loginComponent.inject(this)
        loginComponent.inject(viewModel)
    }

    private fun setLoginActivity() {
        binding.activityLabel.text = getString(R.string.reg_label_1)
        binding.enterButton.text = getString(R.string.regButton_1)

        binding.changeActivity.text = getString(R.string.changeRegButton_1)
        binding.changeActivity.setOnClickListener{ setSignupActivity() }

        binding.enterButton.setOnClickListener {
            val email: String = binding.EmailAddress.text.toString()
            val password: String = binding.Password.text.toString()

            viewModel.signIn(email, password)
        }
    }

    private fun setSignupActivity() {
        binding.activityLabel.text = getString(R.string.reg_label_2)
        binding.enterButton.text = getString(R.string.regButton_2)

        binding.enterButton.setOnClickListener {
            val email: String = binding.EmailAddress.text.toString()
            val password: String = binding.Password.text.toString()

            viewModel.signUp(email, password)
        }

        binding.changeActivity.text = getString(R.string.changeRegButton_2)
        binding.changeActivity.setOnClickListener{ setLoginActivity() }
    }

    private suspend fun listenLoginResultMessage() {
        viewModel.loginResultMessage.collect { result ->
            when(result) {
                is InputDataState.ErrorState -> {
                    Log.e(TAG, result.message)
                    Toast.makeText(baseContext, result.message, Toast.LENGTH_LONG).show()
                }

                is InputDataState.AcceptState -> {
                    startMainActivity(result.email)
                }
            }
        }
    }

    private fun isSigned() : Boolean {
        val email = loginSettings.getString(LOGIN_EMAIL, null)
        return email != null
    }

    private fun startMainActivity(email: String = "") {
        if (email.isNotEmpty()) loginSettings.edit {
            putString(LOGIN_EMAIL, email)
        }

        (router as LoginRouter).navToMainActivity(this@LoginActivity)
        finish()
    }

    companion object {
        const val TAG = "LoginActivity"
        const val LOGIN_SETTINGS = "Login settings"
        const val LOGIN_EMAIL = "Email"
    }
}