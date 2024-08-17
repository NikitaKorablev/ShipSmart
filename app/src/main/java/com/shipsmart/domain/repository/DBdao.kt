package com.shipsmart.domain.repository

import com.shipsmart.domain.model.SupabaseUser

interface DBdao {
    suspend fun getUser(email: String) : SupabaseUser?
    suspend fun addUser(user: SupabaseUser)
}