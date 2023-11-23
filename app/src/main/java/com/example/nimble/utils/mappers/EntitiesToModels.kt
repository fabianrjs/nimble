package com.example.nimble.utils.mappers

import com.example.nimble.database.entities.UserTokenEntity
import com.example.nimble.model.UserToken

fun UserTokenEntity.toUserTokenModel() = UserToken(
    id = this.id,
    accessToken = this.accessToken,
    tokenType = this.tokenType,
    expiresIn = this.expiresIn,
    refreshToken = this.refreshToken,
    createdAt = this.createdAt
)
