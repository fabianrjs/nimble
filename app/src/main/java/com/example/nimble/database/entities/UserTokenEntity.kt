package com.example.nimble.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

const val USER_TOKEN_DB_TABLE_NAME = "user_token"

@Entity(tableName = USER_TOKEN_DB_TABLE_NAME, primaryKeys = ["id", "access_token"])
data class UserTokenEntity(
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "access_token") val accessToken: String,
    @ColumnInfo(name = "token_type") val tokenType: String,
    @ColumnInfo(name = "expires_in") val expiresIn: Long,
    @ColumnInfo(name = "refresh_token") val refreshToken: String,
    @ColumnInfo(name = "created_at") val createdAt: Long
)
