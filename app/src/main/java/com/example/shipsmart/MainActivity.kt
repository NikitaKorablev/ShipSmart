package com.example.shipsmart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.shipsmart.dbLogic.MainDB
import com.example.shipsmart.dbLogic.User

class MainActivity : AppCompatActivity() {
    private var emailInput: EditText? = null
    private var passwordInput: EditText? = null
    private lateinit var db: MainDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Log.d(TAG, "on_create")

        this.db = MainDB.getDB(this)

        supportActionBar?.hide() // what is it?

        emailInput = findViewById<EditText>(R.id.EmailAddress)
        passwordInput = findViewById<EditText>(R.id.Password)
        val enterButton = findViewById<Button>(R.id.login_button)

        enterButton.setOnClickListener(this::onEnterButtonClick)
    }

    private fun onEnterButtonClick(view: View?) {
        Log.d(TAG, "button_is_pressed")
        val email = emailInput?.text.toString()
        val password = passwordInput?.text.toString()

        if (email != "" && password != "") {
            Thread{
                val user = User(null,
                    email,
                    password
                )
                try {
                    db.dao().insetUser(user)
                    Log.d(TAG, db.dao().getAllUsers().toString())

                } catch(err: Exception) {
                    Log.e(TAG, err.message.toString())
                }
            }.start()
        }

    }

    companion object {
        const val TAG = "Main_Activity"
    }
}