package com.example.nimble.network.models

import com.google.gson.annotations.SerializedName

data class UserTokenResponse(
    @SerializedName("data") val data: TokenData
)

data class TokenData(
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String,
    @SerializedName("attributes") val attributes: TokenAttributes
)

data class TokenAttributes(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("expires_in") val expiresIn: Long,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("created_at") val createdAt: Long
)
