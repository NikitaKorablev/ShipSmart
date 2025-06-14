package com.core.delivery_network.data.request_data

import com.google.gson.annotations.SerializedName

data class CDEKDeliveryRequest(
    @SerializedName("payerType") val payerType: String,
    @SerializedName("currencyMark") val currencyMark: String,
    @SerializedName("senderCityId") val senderCityId: String,
    @SerializedName("receiverCityId") val receiverCityId: String,
    @SerializedName("packages") val packages: List<RequestPackage>
): ApiRequest

data class RequestPackage(
    @SerializedName("height") val height: Int,
    @SerializedName("length") val length: Int,
    @SerializedName("width") val width: Int,
    @SerializedName("weight") val weight: Int
)