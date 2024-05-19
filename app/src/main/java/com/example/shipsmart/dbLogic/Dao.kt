package com.example.shipsmart.dbLogic

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface Dao {
    @Insert
    fun insetUser(user: AndroidUser)

    @Query("SELECT * FROM users")
    fun getAllUsers(): List<AndroidUser>

    @Query("SELECT * FROM users WHERE Email = :email")
    fun getUser(email: String): AndroidUser
}