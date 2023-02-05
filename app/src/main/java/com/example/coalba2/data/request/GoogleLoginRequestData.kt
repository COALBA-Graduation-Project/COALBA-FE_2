package com.example.coalba2.data.request

import com.google.gson.annotations.SerializedName

data class GoogleLoginRequestData(
    @SerializedName("grant_type")
    val grantType: String,
    @SerializedName("redirect_uri")
    val redirectUri: String,
    @SerializedName("client_id")
    val clientId: String,
    @SerializedName("client_secret")
    val clientSecret: String,
    @SerializedName("code")
    val code: String
)
