package com.example.nimble.network.services

import com.example.nimble.network.models.LoginRequest
import com.example.nimble.network.models.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("api/v1/oauth/token")
    suspend fun login(@Body loginRequest: LoginRequest) : Response<LoginResponse>

}
