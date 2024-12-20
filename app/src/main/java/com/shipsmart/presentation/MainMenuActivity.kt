package com.shipsmart.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shipsmart.databinding.ActivityMainMenuBinding

class MainMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}