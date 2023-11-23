package com.example.nimble.utils.mappers

import com.example.nimble.database.entities.SurveyEntity
import com.example.nimble.database.entities.UserTokenEntity
import com.example.nimble.model.Survey
import com.example.nimble.model.UserToken

fun UserTokenEntity.toUserTokenModel() = UserToken(
    id = this.id,
    accessToken = this.accessToken,
    tokenType = this.tokenType,
    expiresIn = this.expiresIn,
    refreshToken = this.refreshToken,
    createdAt = this.createdAt
)

fun SurveyEntity.toSurveyModel() = Survey(
    id = this.id,
    title = this.title,
    description = this.description,
    coverImageUrl = this.coverImageUrl
)
