package com.app.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.app.databinding.ActivityPackageChooserBinding
import com.app.domain.ApiResponse
import com.app.domain.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

class PackageChooserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPackageChooserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val arguments = intent.extras

        super.onCreate(savedInstanceState)
        binding = ActivityPackageChooserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var from: String? = ""
        var where: String? = ""
        var length: String? = ""
        var width: String? = ""
        var height: String? = ""

        if (arguments != null) {
            from = arguments.getString("from")
            where = arguments.getString("where")
            length = arguments.getString("length")
            width = arguments.getString("width")
            height = arguments.getString("height")

            Log.d("vals", "$length $width $height")
        } else {
            Log.d("vals", "Arguments are null")
        }

        Log.d("vals", "$length $width $height")

        var url = "https://boxberry.ru/proxy/delivery/cost/pip?method=TarificationLaP"

        url += "&sender_city=68&sender_country=643"
        url += "&receiver_city=33&receiver_country=643"
        url += "&public_price=100000"
        url += "&package[boxberry_package]=0"
        url += "&promo_code=%D0%A0%D0%AF%D0%94%D0%9E%D0%9C"
        url += "&package[width]=$width"
        url += "&package[height]=$height"
        url += "&package[depth]=$length"

        Log.d("URL", url)
        val call = RetrofitClient.instance.getData(url)

        call.enqueue(object : Callback<ApiResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    if (apiResponse != null) {
                        val cost = apiResponse.data.find { it.delivery_type == 4 }?.cost
                        if (cost != null) {
                            val formattedCost = formatCost(cost)
                            binding.boxVal.text = "Cost: $formattedCost"
                        }
                    }
                } else {
                    binding.boxVal.text = "Error: ${response.code()}"
                }
            }

            @SuppressLint("SetTextI18n")
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                binding.boxVal.text = "Error: ${t.message}"
            }
        })
    }

    private fun formatCost(cost: Int): String {
        val decimalFormat = DecimalFormat("#,##0.00")
        return decimalFormat.format(cost / 100.0)
    }
}