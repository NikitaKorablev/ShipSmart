package com.example.shipsmart

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
import com.example.shipsmart.dbLogic.MainDB
import com.example.shipsmart.dbLogic.AuthorisationDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
//    private lateinit var db: MainDB
    private var authorisationDB: AuthorisationDB = AuthorisationDB()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Log.d(TAG, "on_create")

//        this.db = MainDB.getDB(this)
        supportActionBar?.hide() // what is it?

        Log.d(LOGIN_WINDOW, "login_window start")
        this.emailInput = findViewById(R.id.EmailAddress)
        this.passwordInput = findViewById(R.id.Password)
        val enterButton = findViewById<Button>(R.id.login_button)
        val openSignupButton = findViewById<TextView>(R.id.open_signUp)

        if (enterButton == null || openSignupButton == null) Log.e(LOGIN_WINDOW, "EnterButton is undefined.")
        else {
            enterButton.setOnClickListener(this::onEnterButtonClick)
            openSignupButton.setOnClickListener(this::onOpenSignupButtonClick)
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

    private fun onEnterButtonClick(view: View?) {
        Log.d(TAG, "button_is_pressed")
        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            lifecycleScope.launch {
                val user = withContext(Dispatchers.IO) {
                    return@withContext authorisationDB.getUser(email = email)
                }
                if (user == null) createToast("This user undefined!")
                else if (user.password != password) {
                    createToast("Your password is incorrect!")
                } else {
                    createToast("Welcome back!")
                    // TODO: Start MainActivity
                }
            }

//            Thread{
//                try {
//                    val u: AndroidUser? = db.dao().getUser(email)
//                    if (u == null) createToast("This user undefined!")
//                    else if (u.userPassword == password) {
//                        createToast("Добро пожаловать!")
//                        // TODO: Start MainActivity
//                    }
//                } catch(err: Exception) {
//                    createToast("Ошибка входа")
//                    Log.e(TAG, err.message.toString())
//                }
//            }.start()
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
    }
}