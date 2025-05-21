package com.core.delivery_network.data.companies_repos

import android.annotation.SuppressLint
import com.core.delivery_network.data.CityParams
import com.core.delivery_network.data.DeliveryData
import com.core.delivery_network.data.PackageParams
import com.core.delivery_network.data.response_data.city_response_data.CDEKCityData
import com.core.delivery_network.data.companies.CDEK
import com.core.delivery_network.data.request_data.CDEKDeliveryRequest
import com.core.delivery_network.data.request_data.RequestPackage
import com.core.delivery_network.data.response_data.DeliveryResponse
import com.core.delivery_network.data.response_data.city_response_data.CityResponse
import com.core.delivery_network.data.response_data.cost_response_data.DeliveryType
import com.core.delivery_network.domain.delivery_services.CDEKDeliveryService
import com.core.delivery_network.domain.repository.DeliveryRepository
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class CDEKDeliveryRepositoryImpl @Inject constructor(
    private var deliveryService: CDEKDeliveryService
) : DeliveryRepository {
    override val company = CDEK()

    private fun getSessionCookie(): String? {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://cdek.ru/ru/cabinet/calculate/") // Базовый URL для получения cookie
            .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
            .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36")
            .get()
            .build()

        return try {
            val response = client.newCall(request).execute()
            val cookies = response.headers("Set-Cookie")
            if (cookies.isNotEmpty()) {
                cookies.joinToString("; ") { cookie ->
                    cookie.split(";")[0] // Берем только имя и значение cookie
                }
            } else {
                null
            }
        } catch (e: Exception) {
            println("Error getting cookie: ${e.message}")
            null
        }
    }

    override suspend fun getCitiesData(packageParams: PackageParams): CityResponse {
        return try {
            val senderCity = getCityData(packageParams.cityParams.senderCity)
            val receiverCity = getCityData(packageParams.cityParams.receiverCity)

            when {
                senderCity == null -> CityResponse.Error("Sender city is undefined")
                receiverCity == null -> CityResponse.Error("Receiver city is undefined")
                else -> CityResponse.Accept(
                    CityParams(
                        senderCity = senderCity.uuid,
                        receiverCity = receiverCity.uuid,
                        senderCountry = senderCity.countryCode.toString(),
                        receiverCountry = receiverCity.countryCode.toString()
                    )
                )
            }
        } catch (e: Exception) {
            CityResponse.Error("Getting city code exception:\n\t${e.message.toString()}")
        }
    }

    private suspend fun getCityData(cityName: String): CDEKCityData? {
        val url = company.getCityUrl(cityName)
        val dataResp = deliveryService.getCityCodeData(url)

        return dataResp.data.find { it.name == cityName }
    }

    override suspend fun getDeliveryCost(
        packageParams: PackageParams,
    ): DeliveryResponse {
        return try {
            val url = company.getCostUrl(packageParams)

            // Получаем свежий cookie
//            val cookie = getSessionCookie() ?: return DeliveryResponse.Error("Failed to obtain cookie")

            val requestBody = CDEKDeliveryRequest(
                payerType = "sender",
                currencyMark = "RUB",
                senderCityId = packageParams.cityParams.senderCity,
                receiverCityId = packageParams.cityParams.receiverCity,
                packages = listOf(RequestPackage(
                    height = packageParams.height.toInt(),
                    width = packageParams.width.toInt(),
                    length = packageParams.length.toInt(),
                    weight = 100
                ))
            )
            val headers = mapOf(
                "accept" to "application/json",
                "accept-language" to "ru,en;q=0.9",
                "content-type" to "application/json",
//                "cookie" to cookie,
//                "origin" to "https://www.cdek.ru",
//                "priority" to "u=1, i",
//                "referer" to "https://www.cdek.ru/ru/cabinet/calculate/",
//                "sec-ch-ua" to "\"Chromium\";v=\"134\", \"Not:A-Brand\";v=\"24\", \"YaBrowser\";v=\"25.4\", \"Yowser\";v=\"2.5\"",
//                "sec-ch-ua-mobile" to "?0",
//                "sec-ch-ua-platform" to "\"Windows\"",
//                "sec-fetch-dest" to "empty",
//                "sec-fetch-mode" to "cors",
//                "sec-fetch-site" to "same-origin",
//                "user-agent" to "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 YaBrowser/25.4.0.0 Safari/537.36",
//                "x-interface-id" to "cabinet",
//                "x-site-code" to "ru",
//                "x-user-lang" to "rus",
//                "cookie" to "spid=1745345605724_afdf28728f70bb1f5fa131cbc17bae21_231eh2gqnauij7c6; currentLocale=ru; deduplication_cookie=yandex; deduplication_cookie=yandex; ma_id=3143481441742990855543; sbjs_migrations=1418474375998%3D1; sbjs_first_add=fd%3D2025-04-22%2021%3A13%3A29%7C%7C%7Cep%3Dhttps%3A%2F%2Fwww.cdek.ru%2Fru%2F%3Futm_source%3Dyandex%26utm_medium%3Dcpc%26utm_campaign%3D%25D0%259C%25D0%259A_%25D0%2591%25D1%2580%25D0%25B5%25D0%25BD%25D0%25B4%26yclid%3D14123062304113688575%7C%7C%7Crf%3Dhttps%3A%2F%2Fyandex.ru%2F; sbjs_current=typ%3Dutm%7C%7C%7Csrc%3Dyandex%7C%7C%7Cmdm%3Dcpc%7C%7C%7Ccmp%3D%25D0%259C%25D0%259A_%25D0%2591%25D1%2580%25D0%25B5%25D0%25BD%25D0%25B4%7C%7C%7Ccnt%3D%28none%29%7C%7C%7Ctrm%3D%28none%29; sbjs_first=typ%3Dutm%7C%7C%7Csrc%3Dyandex%7C%7C%7Cmdm%3Dcpc%7C%7C%7Ccmp%3D%25D0%259C%25D0%259A_%25D0%2591%25D1%2580%25D0%25B5%25D0%25BD%25D0%25B4%7C%7C%7Ccnt%3D%28none%29%7C%7C%7Ctrm%3D%28none%29; tt_deduplication_cookie=yandex; tt_deduplication_cookie=yandex; tt_deduplication_cookie=yandex; _ym_uid=1745345610244153685; _ym_d=1745345610; advcake_session_id=2b804c56-4919-49f4-4e49-39702cad77a7; advcake_utm_partner=%25D0%259C%25D0%259A_%25D0%2591%25D1%2580%25D0%25B5%25D0%25BD%25D0%25B4; advcake_utm_webmaster=; advcake_click_id=; tmr_lvid=87e5176f8e48164c961015d39502fc34; tmr_lvidTS=1745345609674; flomni_5d713233e8bc9e000b3ebfd2={%22userHash%22:%220e06ddfa-f022-4766-b2a1-3d3b69213bc2%22}; aprt_last_partner=actionpay; aprt_last_apclick=; __upin=h6e/XUdHJeNqu+iRa6cBQg; popmechanic_sbjs_migrations=popmechanic_1418474375998%3D1%7C%7C%7C1471519752600%3D1%7C%7C%7C1471519752605%3D1; _ga=GA1.2.1032276889.1745345652; __ai_fp_uuid=7b92d660d73858f7%3A2; _buzz_aidata=JTdCJTIydWZwJTIyJTNBJTIyaDZlJTJGWFVkSEplTnF1JTJCaVJhNmNCUWclMjIlMkMlMjJicm93c2VyVmVyc2lvbiUyMiUzQSUyMjI1LjQlMjIlMkMlMjJ0c0NyZWF0ZWQlMjIlM0ExNzQ3MTQ5MTQxOTI1JTdE; _buzz_mtsa=JTdCJTIydWZwJTIyJTNBJTIyY2UxNmI2ZTFhZTRiNWY4MjExOTAxMWNmM2M1NDY3NjAlMjIlMkMlMjJicm93c2VyVmVyc2lvbiUyMiUzQSUyMjI1LjQlMjIlMkMlMjJ0c0NyZWF0ZWQlMjIlM0ExNzQ3MTQ5MTQyMTE3JTdE; cpss=eyJ0b2tlbiI6InBoaWVZaWFzaDNpUnUzYWgifQ%3D%3D; _gid=GA1.2.1941479282.1747399643; _ym_isad=2; cityid=250; spsn=1747507530524_7b2276657273696f6e223a22332e332e33222c227369676e223a223766643336653438663236383361623330643330663732636534653965666663222c22706c6174666f726d223a2257696e3332222c2262726f7773657273223a5b226368726f6d65222c2279616e646578225d2c2273636f7265223a302e367d; spsc=1747507530524_9ed10fb7ddd3ac5dbab50d9eb4b45f27_yj3FPWPfzp2-A4QxeP69FncIbHr2FPxrGLbRXnmX1XO9tvKt7t2gvEuF70fduEhGZ; domain_sid=M4qmFz0jPv_J8KD07PDJL%3A1747507536574; sbjs_udata=vst%3D7%7C%7C%7Cuip%3D%28none%29%7C%7C%7Cuag%3DMozilla%2F5.0%20%28Windows%20NT%2010.0%3B%20Win64%3B%20x64%29%20AppleWebKit%2F537.36%20%28KHTML%2C%20like%20Gecko%29%20Chrome%2F134.0.0.0%20YaBrowser%2F25.4.0.0%20Safari%2F537.36; aprt_last_apsource=14123062304113688575; _ym_visorc=b; mindboxDeviceUUID=70216778-33c6-44ff-b3c7-b49b46316940; directCrm-session=%7B%22deviceGuid%22%3A%2270216778-33c6-44ff-b3c7-b49b46316940%22%7D; sbjs_current_add=fd%3D2025-05-17%2022%3A21%3A37%7C%7C%7Cep%3Dhttps%3A%2F%2Fwww.cdek.ru%2Fru%2F%3Futm_source%3Dyandex%26utm_medium%3Dcpc%26utm_campaign%3D%25D0%259C%25D0%259A_%25D0%2591%25D1%2580%25D0%25B5%25D0%25BD%25D0%25B4%26yclid%3D14123062304113688575%26utm_referrer%3Dhttps%253a%252f%252fyandex.ru%252f%7C%7C%7Crf%3Dhttps%3A%2F%2Fwww.cdek.ru%2Fru%2F%3Futm_source%3Dyandex%26utm_medium%3Dcpc%26utm_campaign%3D%25D0%259C%25D0%259A_%25D0%2591%25D1%2580%25D0%25B5%25D0%25BD%25D0%25B4%26yclid%3D14123062304113688575%26utm_referrer%3Dhttps%253a%252f%252fyandex.ru%252f; sbjs_session=pgs%3D2%7C%7C%7Ccpg%3Dhttps%3A%2F%2Fwww.cdek.ru%2Fru%2F%3Futm_source%3Dyandex%26utm_medium%3Dcpc%26utm_campaign%3D%25D0%259C%25D0%259A_%25D0%2591%25D1%2580%25D0%25B5%25D0%25BD%25D0%25B4%26yclid%3D14123062304113688575%26utm_referrer%3Dhttps%253a%252f%252fyandex.ru%252f; advcake_track_id=65c100a4-4f07-2895-7776-a401d145ce85; tmr_detect=0%7C1747509699748; _gat_UA-109747035-4=1; _ga_ST44CDZHG6=GS2.2.s1747509613\$o6\$g1\$t1747509725\$j0\$l0\$h0; advcake_track_url=%3D20250113zhQDC15xZNannPltmaKZmTz00coDzr%2B4SExyKqaNtLpBd6eSXrgjnReYSvlyBYhHoPfuQFrvmoBSkVHGZP8Pc7vNS4HDQHvzRGk9lg3QUJTDI1sgNrjGBN2ChULULwJbnBTgD23qXw4HXX09hGNzhcDkmjbu57UDgz%2BNOFnVTRi%2FJKQfQ8Zo5uonIDgJ6XJ39mF7ORZdJTFggDkditt68f6GDJOuEtZ33alYxe3NxEL5lmDGL6PJuiyMAfo4GL8T1xp5I0sLNnAUhC%2B9jHr7hv4TOqSC%2F7FR1G9%2BoPUB1CAdNcJPzJRRUVOPQcs4Q1FZtGxbh2yULoqvCpdm7kDe6GvZ34HQzOoOwdbMwGJpQ3gwpLOTj%2FJiCQkGbqJQXSIs6iHBlQn9D3pV%2FBb1kOgnkM2qIQ1bI5IF1ajS3tIwwqoWStt51wUcho%2BtduVWGvT14KWJi54MR1kqQ8f7UTPpYBzdD%2Fwhz1NWkTuijH5fb2Y6%2F4OAeJ7ZAza8GVaKh6wHNz%2Bw1kquro6WfCSU4YlF7WIRfPDybPKOnfe9sYA4EWLlQyDpFd84Wajgh16ZOnP3cFkCDiP%2FZIjsZ8%2Bl7Br9CSdjDUGvu%2FmGR%2Fh2WjalTF%2FKHMphSs2DpKl66Sq2jKIp79VpaOiu4idOr90SHM3zUcxI%2BoYVgP0ThrHLo0GhfdFJHW6TXdc9XNg%3D; affinity_wa=1747509732.804.773112.853419|1733b77ab82f0285d5c5be129a471ad5"
            )

            val response = deliveryService.getCostData(url, headers, requestBody)
            if (response.data.isEmpty())
                DeliveryResponse.Error("Getting delivery cost exception: Cost not found")
            val delivery = getCheapestDelivery(response.data)

            val cost = delivery.minPrice
            val deliveryTime = delivery.minDays

            DeliveryResponse.Accept(
                DeliveryData(
                    name = company.name,
                    cost = formatCost(cost.toDouble()),
                    deliveryTime = deliveryTime.toString(),
                    img = company.imgResource
                )
            )
        } catch (e: Exception) {
            DeliveryResponse.Error("Getting CDEK delivery cost exception: ${e.message}")
        }
    }

    private fun getCheapestDelivery(deliveryTypes: List<DeliveryType>): DeliveryType {
        var delivery: DeliveryType = deliveryTypes[0]

        for (i in 1..<deliveryTypes.size) {
            if (deliveryTypes[i].minPrice < delivery.minPrice)
                delivery = deliveryTypes[i]
        }

        return delivery
    }

    @SuppressLint("DefaultLocale")
    private fun formatCost(cost: Double): String {
        return String.format("%.2f", cost / 100.0)
    }
}