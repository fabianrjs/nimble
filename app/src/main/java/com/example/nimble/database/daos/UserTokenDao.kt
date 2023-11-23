package com.example.nimble.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.nimble.database.entities.USER_TOKEN_DB_TABLE_NAME
import com.example.nimble.database.entities.UserTokenEntity

@Dao
interface UserTokenDao {

    @Query(
        "SELECT * " +
        "FROM $USER_TOKEN_DB_TABLE_NAME " +
        "WHERE ${USER_TOKEN_DB_TABLE_NAME}.access_token = :accessToken"
    )
    fun getUserTokenByAccessToken(accessToken: String): UserTokenEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserToken(userToken: UserTokenEntity)

    @Update
    fun updateUserToken(userToken: UserTokenEntity)

    @Delete
    fun deleteUserToken(userToken: UserTokenEntity)
}
