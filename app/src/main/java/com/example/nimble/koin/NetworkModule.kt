package com.example.nimble.koin

import com.example.nimble.network.NimbleRetrofit.retrofit
import com.example.nimble.network.services.AuthService
import com.example.nimble.network.repositories.AuthRepository
import com.example.nimble.network.repositories.UserRepository
import com.example.nimble.network.services.UserService
import org.koin.dsl.module

val networkModule = module {

    single { AuthRepository(get()) }
    single { UserRepository(get()) }

    single { retrofit.create(AuthService::class.java) }
    single { retrofit.create(UserService::class.java) }
}
