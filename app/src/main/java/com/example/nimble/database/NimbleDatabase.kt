package com.example.nimble.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nimble.database.daos.UserTokenDao
import com.example.nimble.database.entities.UserTokenEntity

const val NIMBLE_DATABASE_NAME = "nimble_database"

@Database(entities = [UserTokenEntity::class], version = 1)
abstract class NimbleDatabase : RoomDatabase() {

    abstract fun userTokenDao(): UserTokenDao

}
