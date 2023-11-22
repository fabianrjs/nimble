package com.example.nimble.network.repositories

import com.example.nimble.network.services.SurveysService
import kotlinx.coroutines.flow.flow

const val DEFAULT_PAGE_NUMBER = 1
const val DEFAULT_PAGE_SIZE = 5

class SurveysRepository(
    private val surveysService: SurveysService
) {
    fun getSurveysList(
        authorization: String,
        pageNumber: Int = DEFAULT_PAGE_NUMBER,
        pageSize: Int = DEFAULT_PAGE_SIZE
    ) = flow {
        emit(
            surveysService.getSurveysList(
                authorization = authorization,
                pageNumber = pageNumber,
                pageSize = pageSize
            )
        )
    }
}