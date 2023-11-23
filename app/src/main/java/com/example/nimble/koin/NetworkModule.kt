package com.example.nimble.koin

import com.example.nimble.network.interceptors.AuthInterceptor
import com.example.nimble.network.services.AuthService
import com.example.nimble.network.repositories.AuthRepository
import com.example.nimble.network.repositories.SurveysRepository
import com.example.nimble.network.repositories.UserRepository
import com.example.nimble.network.services.SurveysService
import com.example.nimble.network.services.UserService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://survey-api.nimblehq.co/"

val networkModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder().addInterceptor(AuthInterceptor()).build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { AuthRepository(get()) }
    single { UserRepository(get()) }
    single { SurveysRepository(get()) }

    single { get<Retrofit>().create(AuthService::class.java) }
    single { get<Retrofit>().create(UserService::class.java) }
    single { get<Retrofit>().create(SurveysService::class.java) }
}
