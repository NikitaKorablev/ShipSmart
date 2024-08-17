package com.shipsmart.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SupabaseUser(
    var id: Int? = null,
    var name: String? = null,
    var email: String,
    var password: String
)
