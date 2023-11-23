package com.example.nimble.network.models

import com.google.gson.annotations.SerializedName

data class ResetPasswordBodyRequest(
    @SerializedName("user") val user: UserEmail,
    @SerializedName("client_id") val clientId: String = "ofzl-2h5ympKa0WqqTzqlVJUiRsxmXQmt5tkgrlWnOE",
    @SerializedName("client_secret") val clientSecret: String = "lMQb900L-mTeU-FVTCwyhjsfBwRCxwwbCitPob96cuU"
)

data class UserEmail(
    @SerializedName("email") val email: String
)
