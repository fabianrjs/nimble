package com.example.nimble.network.repositories

import com.example.nimble.network.services.UserService
import kotlinx.coroutines.flow.flow

class UserRepository(
    private val userService: UserService
) {

    suspend fun getUserInfo(authorization: String) = flow {
        emit(userService.getUserInfo(authorization))
    }
}
