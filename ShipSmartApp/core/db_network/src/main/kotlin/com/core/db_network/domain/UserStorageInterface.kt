package com.core.db_network.domain

import com.core.db_network.data.storage.SupabaseUser

interface UserStorageInterface {
    suspend fun getUser(email: String) : SupabaseUser?
    suspend fun addUser(user: SupabaseUser) : Boolean
}