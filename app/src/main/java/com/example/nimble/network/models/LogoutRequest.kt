package com.example.nimble.network.models

import com.google.gson.annotations.SerializedName

data class LogoutRequest(
    @SerializedName("token") val token: String,
    @SerializedName("client_id") val clientId: String = "ofzl-2h5ympKa0WqqTzqlVJUiRsxmXQmt5tkgrlWnOE",
    @SerializedName("client_secret") val clientSecret: String = "lMQb900L-mTeU-FVTCwyhjsfBwRCxwwbCitPob96cuU"
)
