package com.shipsmart.domain

import com.shipsmart.data.dbLogic.SupabaseUser

interface dbDAO {
    suspend fun getUser(email: String) : SupabaseUser?
    suspend fun addUser(user: SupabaseUser)
}