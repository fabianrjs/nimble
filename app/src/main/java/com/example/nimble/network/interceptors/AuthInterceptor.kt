package com.example.nimble.network.interceptors

import com.example.nimble.koin.BASE_URL
import com.example.nimble.network.SessionManager
import com.example.nimble.utils.getAuthorization
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (SessionManager.isAccessTokenExpired() && chain.request().url.toString() != (BASE_URL + "api/v1/oauth/token")) {
            val newAccessToken =
                runBlocking {
                    SessionManager.refreshToken()
                }
            val newRequest = chain.request().newBuilder()
                .header("Authorization", newAccessToken.getAuthorization())
                .build()

            return chain.proceed(newRequest)
        }
        return chain.proceed(chain.request())
    }
}
