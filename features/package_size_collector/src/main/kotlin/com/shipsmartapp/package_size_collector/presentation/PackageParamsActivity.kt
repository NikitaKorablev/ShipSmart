package com.shipsmartapp.package_size_collector.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.core.delivery_network.data.CityParams
import com.core.delivery_network.data.PackageParams
import com.core.utils.Router
import com.shipsmartapp.package_size_collector.R
import com.shipsmartapp.package_size_collector.databinding.ActivityPackageParamsBinding
import com.shipsmartapp.package_size_collector.di.PackageCollectorDepsProvider
import com.shipsmartapp.package_size_collector.utils.PackageParamsCollectionRouter
import javax.inject.Inject

class PackageParamsActivity : AppCompatActivity() {

    @Inject
    lateinit var router: Router
    private lateinit var binding: ActivityPackageParamsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPackageParamsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, systemBars.bottom)
            insets
        }

        initDI()

        binding.calculate.setOnClickListener(this::onCalculateButtonClicked)
    }

    private fun initDI() {
        val component = (applicationContext as PackageCollectorDepsProvider)
            .getPackageCollectorComponent()
        component.inject(this)
    }

    private fun onCalculateButtonClicked(view: View?) {
        val packageParams = getPackageData(binding)

        (router as PackageParamsCollectionRouter)
            .navToDeliveryChoosingActivity(this@PackageParamsActivity, packageParams)
    }

    private fun getPackageData(binding: ActivityPackageParamsBinding)
    : PackageParams {
        val cityData = CityParams(
            senderCity = binding.cityFrom.text.toString().trim(),
            receiverCity = binding.cityWhere.text.toString().trim(),
            senderCountry = BASE_COUNTRY,
            receiverCountry = BASE_COUNTRY
        )

        return PackageParams(
            height = binding.heightValue.text.toString().trim(),
            width = binding.widthValue.text.toString().trim(),
            length = binding.lengthValue.text.toString().trim(),

            cityParams = cityData
        )
    }

    companion object {
        const val BASE_COUNTRY = "Россия"
    }
}