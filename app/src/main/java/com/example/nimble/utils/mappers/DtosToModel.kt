package com.example.nimble.utils.mappers

import com.example.nimble.model.UserToken
import com.example.nimble.network.models.UserTokenResponse

fun UserTokenResponse.toUserTokenModel() = UserToken(
    id = this.data.id,
    accessToken = this.data.attributes.accessToken,
    tokenType = this.data.attributes.tokenType,
    expiresIn = this.data.attributes.expiresIn,
    refreshToken = this.data.attributes.refreshToken,
    createdAt = this.data.attributes.createdAt
)
