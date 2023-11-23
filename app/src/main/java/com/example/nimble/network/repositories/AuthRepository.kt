package com.example.nimble.network.repositories

import com.example.nimble.network.services.AuthService
import com.example.nimble.network.models.LoginRequest
import com.example.nimble.network.models.LogoutRequest
import com.example.nimble.network.models.ResetPasswordBodyRequest
import com.example.nimble.network.models.UserEmail
import kotlinx.coroutines.flow.flow

class AuthRepository(
    private val authService: AuthService
) {

    suspend fun login(email: String, password: String) = flow {
        emit(authService.login(LoginRequest(email = email, password = password)))
    }

    fun logout(token: String) = flow {
        emit(authService.logout(LogoutRequest(token = token)))
    }

    fun resetPassword(email: String) = flow {
        emit(authService.resetPassword(ResetPasswordBodyRequest(UserEmail(email))))
    }

}
