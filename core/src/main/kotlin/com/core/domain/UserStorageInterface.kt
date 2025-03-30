package com.core.domain

import com.core.data.SupabaseUser

interface UserStorageInterface {
    suspend fun getUser(email: String) : SupabaseUser?
    suspend fun addUser(user: SupabaseUser) : Boolean
}