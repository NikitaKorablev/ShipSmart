package com.example.shipsmart.dbLogic

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (
    entities = [AndroidUser::class],
    version = 1
//    autoMigrations = [
//        AutoMigration(from = 1, to = 2)
//    ]
    )
abstract class MainDB: RoomDatabase() {
    abstract fun dao(): Dao
    companion object {
        fun getDB(context: Context): MainDB {
            return Room.databaseBuilder(
                context.applicationContext,
                MainDB::class.java,
                "test2.db"
            ).build()
        }
    }

}