package com.example.nimble.network.models

import com.google.gson.annotations.SerializedName

data class RefreshTokenRequest(
    @SerializedName("grant_type") val grantType: String = "refresh_token",
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("client_id") val clientId: String = "ofzl-2h5ympKa0WqqTzqlVJUiRsxmXQmt5tkgrlWnOE",
    @SerializedName("client_secret") val clientSecret: String = "lMQb900L-mTeU-FVTCwyhjsfBwRCxwwbCitPob96cuU"
)
