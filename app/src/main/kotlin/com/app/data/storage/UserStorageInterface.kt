package com.app.data.storage

import com.app.data.storage.model.SupabaseUser

interface UserStorageInterface {
    suspend fun getUser(email: String) : SupabaseUser?
    suspend fun addUser(user: SupabaseUser) : Boolean
}