package com.shipsmart.data.dbLogic

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (
    entities = [AndroidUser::class],
    version = 1
//    autoMigrations = [
//        AutoMigration(from = 1, to = 2)
//    ]
    )
abstract class AndroidDB: RoomDatabase() {
    abstract fun dao(): AndroidDAO
    companion object {
        fun getDB(context: Context): AndroidDB {
            return Room.databaseBuilder(
                context.applicationContext,
                AndroidDB::class.java,
                "test2.db"
            ).build()
        }
    }
}