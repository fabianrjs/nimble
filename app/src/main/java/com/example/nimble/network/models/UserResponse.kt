package com.example.nimble.network.models

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("data") val userData: UserData
)

data class UserData(
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String,
    @SerializedName("attributes") val attributes: UserAttributes
)

data class UserAttributes(
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
    @SerializedName("avatar_url") val avatarUrl: String
)
