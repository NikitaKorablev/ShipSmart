package com.shipsmart.data.storage

import com.shipsmart.data.storage.model.SupabaseUser

interface UserStorageInterface {
    suspend fun getUser(email: String) : SupabaseUser?
    suspend fun addUser(user: SupabaseUser) : Boolean
}