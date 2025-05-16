package com.core.delivery_network.data.companies_repos.cost_response_data

data class CDEKApiResponse (
    val status: Int,
    val err: List<Any>,
    val data: List<BoxberryDeliveryData>
)

