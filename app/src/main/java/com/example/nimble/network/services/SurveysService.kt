package com.example.nimble.network.services

import com.example.nimble.network.models.SurveysListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SurveysService {

    @GET("api/v1/surveys")
    suspend fun getSurveysList(
        @Header("Authorization") authorization: String,
        @Query("page[number]") pageNumber: Int,
        @Query("page[size]") pageSize: Int,
    ): Response<SurveysListResponse>
}
