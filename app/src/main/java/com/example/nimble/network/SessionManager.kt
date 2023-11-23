package com.example.nimble.network

import com.example.nimble.database.repositories.AuthDatabaseRepository
import com.example.nimble.datastore.AuthStorageRepository
import com.example.nimble.model.UserToken
import com.example.nimble.model.expiresAt
import com.example.nimble.network.models.RefreshTokenRequest
import com.example.nimble.network.services.AuthService
import com.example.nimble.utils.mappers.toUserTokenModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

object SessionManager : KoinComponent {

    private val authStorageRepository: AuthStorageRepository = get()
    private val authDatabaseRepository: AuthDatabaseRepository = get()
    private val authService: AuthService = get()

    private var userToken: UserToken? = null

    fun setUserToken(userToken: UserToken?) {
        this.userToken = userToken
    }

    fun getUserToken() = this.userToken

    fun isAccessTokenExpired(): Boolean {
        userToken?.let {
            return (System.currentTimeMillis() / 1000) >= it.expiresAt()
        }
        return false
    }

    suspend fun refreshToken(): String {
        val response = authService.refreshToken(RefreshTokenRequest(refreshToken = userToken!!.refreshToken))
        if (response.isSuccessful && response.body() != null) {
            val newUserToken = response.body()!!.toUserTokenModel()
            authStorageRepository.saveUserAccessToken(newUserToken.accessToken)
            authDatabaseRepository.deleteUserToken(userToken!!)
            authDatabaseRepository.saveUserToken(newUserToken)
            userToken = newUserToken

            return userToken!!.accessToken
        }
        return userToken?.accessToken ?: ""
    }

    fun getAccessToken() = userToken?.accessToken ?: ""

}