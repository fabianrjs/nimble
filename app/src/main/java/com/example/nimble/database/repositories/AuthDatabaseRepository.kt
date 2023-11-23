package com.example.nimble.database.repositories

import com.example.nimble.database.daos.UserTokenDao
import com.example.nimble.model.UserToken
import com.example.nimble.utils.mappers.toUserTokenEntity
import com.example.nimble.utils.mappers.toUserTokenModel
import kotlinx.coroutines.flow.flow

class AuthDatabaseRepository(
    private val userTokenDao: UserTokenDao
) {

    suspend fun getUserToken(accessToken: String) = flow {
        emit(userTokenDao.getUserTokenByAccessToken(accessToken)?.toUserTokenModel())
    }

    fun saveUserToken(userToken: UserToken) {
        userTokenDao.insertUserToken(userToken.toUserTokenEntity())
    }

    fun deleteUserToken(userToken: UserToken) {
        userTokenDao.deleteUserToken(userToken.toUserTokenEntity())
    }

}
