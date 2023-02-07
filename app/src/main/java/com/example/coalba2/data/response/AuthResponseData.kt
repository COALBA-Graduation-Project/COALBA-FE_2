package com.example.coalba2.data.response

data class AuthResponseData(
    val accessToken : String,
    val refreshToken : String,
    val isNewUser : Boolean
)
