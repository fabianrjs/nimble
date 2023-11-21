package com.example.nimble.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NimbleRetrofit {

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://survey-api.nimblehq.co/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
