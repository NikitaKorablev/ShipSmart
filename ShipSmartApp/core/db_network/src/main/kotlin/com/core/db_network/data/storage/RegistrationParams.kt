package com.core.db_network.data.storage

data class RegistrationParams(
    var id: Int? = null,
    var name: String? = null,
    var email: String,
    var password: String
)