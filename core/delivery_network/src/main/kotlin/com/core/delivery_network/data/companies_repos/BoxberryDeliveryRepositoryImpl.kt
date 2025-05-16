package com.core.delivery_network.data.companies_repos

import android.net.http.HttpException
import android.os.Build
import android.annotation.SuppressLint
import androidx.annotation.RequiresExtension
import com.core.delivery_network.data.CityParams
import com.core.delivery_network.data.PackageData
import com.core.delivery_network.data.DeliveryData
import com.core.delivery_network.data.PackageExtraParams
import com.core.delivery_network.domain.BoxberryDeliveryService
import com.core.delivery_network.data.companies.Boxberry
import com.core.delivery_network.data.companies_repos.city_response_data.BoxberryCity
import com.core.delivery_network.data.companies_repos.city_response_data.BoxberryCountry
import com.core.delivery_network.data.companies_repos.city_response_data.CityResponse
import com.core.delivery_network.domain.BoxberryCityCodeService
import com.core.delivery_network.domain.repository.DeliveryRepository
import javax.inject.Inject

class BoxberryDeliveryRepositoryImpl @Inject constructor(
    private var cityCodeService: BoxberryCityCodeService,
    private var deliveryService: BoxberryDeliveryService
): DeliveryRepository {
    override val company = Boxberry()

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getCitiesData(cityParams: PackageData): CityResponse {
        return try {
            val url = company.getCityUrl()
            val response = cityCodeService.getData(url)
            val (senderCountry, receiverCountry) = getCountryData(
                cityParams.cityData.senderCountry,
                cityParams.cityData.receiverCountry,
                response.country
            )
            val (senderCity, receiverCity) = getCityData(
                cityParams.cityData.senderCity,
                cityParams.cityData.receiverCity,
                response.cities
            )

            if (senderCity == null) CityResponse.Error("Sender city is undefined")
            if (receiverCity == null) CityResponse.Error("Receive city is undefined")
            if (senderCountry == null) CityResponse.Error("Sender country is undefined")
            if (receiverCountry == null) CityResponse.Error("Receive country is undefined")

            CityResponse.Accept(
                CityParams(
                    senderCity!!.code.toInt(),
                    senderCountry!!.code.toInt(),
                    receiverCity!!.code.toInt(),
                    receiverCountry!!.code.toInt()
                )
            )
        } catch (e: HttpException) {
            CityResponse.Error("Getting city code exception:\n\t${e.message.toString()}")
        } catch (e: Exception) {
            CityResponse.Error("Getting city code exception:\n\t${e.message.toString()}")
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getDeliveryCost(
        packageParams: PackageExtraParams,
    ): DeliveryResponse {
        return try {
            val url = company.getCostUrl(packageParams)
            val response = deliveryService.getData(url)

            val data = response.data.find { it.delivery_type == 4 }
            val cost = data?.cost
            val deliveryTime = data?.time

            if (cost != null && deliveryTime != null) {
                val formattedCost = formatCost(cost)
                val deliveryData = DeliveryData(
                    name=company.name,
                    cost=formattedCost,
                    deliveryTime=deliveryTime,
                    img = company.imgResource
                )
                DeliveryResponse.Accept(deliveryData)
            } else {
                DeliveryResponse.Error("Getting delivering cost exception:\n\tCost not found")
            }
        } catch (e: HttpException) {
            DeliveryResponse.Error("Getting delivering cost exception:\n\t${e.message.toString()}")
        } catch (e: Exception) {
            DeliveryResponse.Error("Getting delivering cost exception:\n\t${e.message.toString()}")
        }
    }

    /**
     * @return
     * Sender country data, receiver country data
     */
    private fun getCountryData(
        senderCountryName: String?,
        receiverCountryName: String?,
        countries: List<BoxberryCountry>
    ): Pair<BoxberryCountry?, BoxberryCountry?> {
        if (senderCountryName == null && receiverCountryName == null)
            return Pair(null, null)

        var sender: BoxberryCountry? = null
        var receiver: BoxberryCountry? = null

        for (country in countries) {
            if (country.name == senderCountryName){
                sender = country
                if (receiver != null || receiverCountryName == null) break
            }
            if (country.name == receiverCountryName){
                receiver = country
                if (sender != null || senderCountryName == null) break
            }
        }
        return Pair(sender, receiver)
    }


    /**
     * @return
     * Sender city data, receiver city data
     */
    private fun getCityData(
        senderCityName: String?,
        receiverCityName: String?,
        cities: List<BoxberryCity>
    ): Pair<BoxberryCity?, BoxberryCity?> {
        if (senderCityName == null && receiverCityName == null)
            return Pair(null, null)

        var sender: BoxberryCity? = null
        var receiver: BoxberryCity? = null

        for (city in cities) {
            if (city.name == senderCityName){
                sender = city
                if (receiver != null || receiverCityName == null) break
            }
            if (city.name == receiverCityName){
                receiver = city
                if (sender != null || senderCityName == null) break
            }
        }
        return Pair(sender, receiver)
    }

    @SuppressLint("DefaultLocale")
    private fun formatCost(cost: Double): String {
        return String.format("%.2f", cost/100.0)
    }
}