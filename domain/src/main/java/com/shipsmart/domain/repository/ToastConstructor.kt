package com.example.domain.repository

import android.content.Context
import android.util.Log
import android.widget.Toast

class ToastConstructor(private val context: Context) {
    fun show(message: String) {
        Log.e(TEST, "context: $context, message: $message")

        (context as? androidx.appcompat.app.AppCompatActivity)?.runOnUiThread {
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        const val TAG = "ToastConstructor"
        const val TEST = "ToastConstructorTest"
    }
}