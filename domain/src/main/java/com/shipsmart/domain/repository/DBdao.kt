package com.example.domain.repository

import com.example.domain.model.SupabaseUser

interface DBdao {
    suspend fun getUser(email: String) : SupabaseUser?
    suspend fun addUser(user: SupabaseUser)
}