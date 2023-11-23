package com.example.nimble.database.repositories

import com.example.nimble.database.daos.SurveyDao
import com.example.nimble.model.Survey
import com.example.nimble.utils.mappers.toSurveyEntity
import com.example.nimble.utils.mappers.toSurveyModel
import kotlinx.coroutines.flow.flow

class SurveysDatabaseRepository(
    private val surveyDao: SurveyDao
) {
    suspend fun getSurveys() = flow {
        emit(surveyDao.getSurveys().map { it.toSurveyModel() })
    }

    fun saveSurveys(surveys: List<Survey>) {
        surveyDao.saveSurveys(surveys.map { it.toSurveyEntity() })
    }

}