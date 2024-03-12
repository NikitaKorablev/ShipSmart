package com.example.shipsmart.dbLogic

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity (
    tableName = "users",
    indices = [Index("id")]
    )
data class User(
    @PrimaryKey(autoGenerate = false) var id: Int? = null,
    @ColumnInfo(name = "Email") var userEmail: String? = null,
    @ColumnInfo(name = "Password") var userPassword: String? = null
//    @PrimaryKey(autoGenerate = true) var userId: Int? = null,
//    @ColumnInfo(name = "Name") var userName: String? = null,
//    @ColumnInfo(name = "Email") var userEmail: String? = null,
//    @ColumnInfo(name = "Password") var userPassword: String? = null
)
