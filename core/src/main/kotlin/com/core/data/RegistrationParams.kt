package com.core.data

data class RegistrationParams(
    var id: Int? = null,
    var name: String? = null,
    var email: String,
    var password: String
)