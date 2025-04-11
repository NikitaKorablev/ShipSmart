package com.shipsmartapp.package_size_collector.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.shipsmartapp.package_size_collector.databinding.ActivityPackageParamsBinding

class PackageParamsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPackageParamsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPackageParamsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculate.setOnClickListener(this::onCalculateButtonClicked)
    }

    private fun onCalculateButtonClicked(view: View?) {
        val activity = Intent(baseContext, PackageChooserActivity::class.java)
        val height = binding.heightValue.text.toString()
        val width = binding.widthValue.text.toString()
        val length = binding.lengthValue.text.toString()

        Log.d("vals", "$length $width $height")

        activity.putExtra("height", height)
        activity.putExtra("width", width)
        activity.putExtra("length", length)
        activity.putExtra("from", binding.cityFrom.text)
        activity.putExtra("where", binding.cityWhere.text)

        startActivity(activity)
    }
}