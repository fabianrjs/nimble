package com.example.nimble.network.models

import com.google.gson.annotations.SerializedName

data class ResetPasswordResponse(
    @SerializedName("meta") val meta: MetaMessage
)

data class MetaMessage(
    @SerializedName("message") val message: String
)
