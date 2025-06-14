package com.shipsmartapp.delivery_choosing.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.core.delivery_network.data.CityParams
import com.core.delivery_network.data.DeliveryData
import com.core.delivery_network.data.PackageParams
import com.delivery_choosing.R
import com.delivery_choosing.databinding.ActivityDeliveryChooserBinding
import com.core.delivery_network.data.response_data.DeliveryResponse
import com.shipsmartapp.delivery_choosing.di.DeliveryDepsProvider
import com.shipsmartapp.delivery_choosing.presentation.viewmodel.DeliveryChooserViewModel
import kotlinx.coroutines.launch

class DeliveryChooserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeliveryChooserBinding
    private val viewModel: DeliveryChooserViewModel by viewModels()
    private lateinit var recyclerAdapter: DeliveryChooserRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityDeliveryChooserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.delivery_chooser_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, systemBars.bottom)
            insets
        }

        initDI()
        initRecyclerView()

        lifecycleScope.launch {
            listenDeliveryCost()
        }

        val packageParams = getPackageExtraParams(intent.extras)
        Log.d(TAG, packageParams.toString())
        viewModel.getDeliveryCost(packageParams)
    }

    private fun initDI() {
        val deliveryComponent =
            (applicationContext as DeliveryDepsProvider).getDeliveryComponent()
        deliveryComponent.inject(this)
        deliveryComponent.inject(viewModel)
    }

    private fun initRecyclerView() {
        recyclerAdapter = DeliveryChooserRecyclerAdapter()
        binding.recyclerView.adapter = recyclerAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private suspend fun listenDeliveryCost() {
        viewModel.deliveryCost.collect { result ->
            when(result) {
                is DeliveryResponse.Accept ->
                    updateCompanyList(result.data)
                is DeliveryResponse.Error -> {
                    Log.e(TAG, result.message)
                    Toast.makeText(baseContext, result.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun updateCompanyList(company: DeliveryData) {
        val companies = recyclerAdapter.companyList.toMutableList()
        companies.add(company)

        recyclerAdapter.companyList = companies
    }

    private fun getPackageExtraParams(bundle: Bundle?): PackageParams {
        val baseParams = PackageParams(
            length = "15",
            width = "15",
            height = "15",

            cityParams = CityParams(
                senderCountry = "Россия",
                receiverCountry = "Россия",
                senderCity = "Нижний Новгород",
                receiverCity = "Москва",
            )
        )

        return bundle?.let {
            PackageParams(
                length = it.getString("length") ?: baseParams.length,
                width = it.getString("width") ?: baseParams.width,
                height = it.getString("height") ?: baseParams.height,
                cityParams = CityParams(
                    senderCountry = baseParams.cityParams.senderCountry,
                    receiverCountry = baseParams.cityParams.receiverCountry,
                    senderCity = it.getString("sender_city")
                        ?: baseParams.cityParams.senderCity,
                    receiverCity = it.getString("receiver_city")
                        ?: baseParams.cityParams.receiverCity,
                )
            )
        } ?: baseParams
    }

    companion object {
        const val TAG = "DeliveryChooserActivity"
    }
}