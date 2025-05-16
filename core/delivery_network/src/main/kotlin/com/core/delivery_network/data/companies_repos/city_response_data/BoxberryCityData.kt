package com.core.delivery_network.data.companies_repos.city_response_data

data class BoxberryCityData(
    val country: List<BoxberryCountry>,
    val cities: List<BoxberryCity>
)

data class BoxberryCountry(
    val name: String,
    val code: String,
    val mask_phone: String,
    val min_cost: Int,
    val max_cost: Int,
    val custom: Boolean,
    val currency: String?,
    val show_service: Boolean,
    val text_modal_window: String?,
    val text_modal_button: String?,
    val show_modal: Boolean,
    val title_modal_window: String?,
    val test_import_cost_on_prod: Boolean
)

data class BoxberryCity(
    val name: String,
    val code: String,
    val country_code: String,
    val reception_lap: Boolean,
    val delivery_lap: Boolean,
    val reception: Boolean,
    val pickup_point: Boolean,
    val region: String,
    val region_prefix: String,
    val prefix: String
)