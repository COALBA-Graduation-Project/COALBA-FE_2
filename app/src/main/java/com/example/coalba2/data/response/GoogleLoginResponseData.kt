package com.example.coalba2.data.response

import com.google.gson.annotations.SerializedName

data class GoogleLoginResponseData(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String
)
