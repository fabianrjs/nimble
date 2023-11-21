package com.example.nimble.network.services

import com.example.nimble.network.models.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface UserService {

    @GET("api/v1/me")
    suspend fun getUserInfo(
        @Header("Authorization") authorization: String
    ): Response<UserResponse>

}
