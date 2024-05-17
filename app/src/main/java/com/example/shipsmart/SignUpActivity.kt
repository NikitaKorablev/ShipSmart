package com.example.shipsmart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.shipsmart.dbLogic.MainDB
import com.example.shipsmart.dbLogic.User

class SignUpActivity : AppCompatActivity() {
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var db: MainDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        this.db = MainDB.getDB(this)
        supportActionBar?.hide() // what is it?

        Log.d(SIGNUP_WINDOW, "signup_window start")
        this.emailInput = findViewById(R.id.EmailAddress)
        this.passwordInput = findViewById(R.id.Password)
        val enterButton = findViewById<Button>(R.id.signup_button)
        val openLoginButton = findViewById<TextView>(R.id.open_login)

        if (enterButton == null || openLoginButton == null) Log.e(SIGNUP_WINDOW, "EnterButton is undefined.")
        else {
            enterButton.setOnClickListener(this::onSignUpButtonClick)
            openLoginButton.setOnClickListener(this::onOpenLoginButtonClick)
        }
    }

    private fun createToast(message: String) {
        runOnUiThread {
            Toast.makeText(
                applicationContext,
                message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun onSignUpButtonClick(view: View?) {
        Log.d(TAG, "button_is_pressed")
        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()

        if (email != "" && password != "") {
            Thread{
                val user = User(null,
                    email,
                    password
                )
                try {
                    db.dao().insetUser(user)
                    Log.d(TAG, db.dao().getAllUsers().toString())
                    createToast("Пользователь создан!")
                } catch(err: Exception) {
                    createToast("Ошибка создания пользователя!")
                    Log.e(TAG, err.message.toString())
                }
            }.start()
        }
    }

    private fun onOpenLoginButtonClick(view: View?) {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        startActivity(intent)
    }

    companion object {
        const val TAG = "Main_Activity"
        const val SIGNUP_WINDOW = "SignUp_Window"
    }
}