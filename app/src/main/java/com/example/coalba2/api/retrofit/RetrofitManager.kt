package com.example.coalba2.api.retrofit

import com.example.coalba2.api.service.AuthService

class RetrofitManager {
    companion object {
        val authService = RetrofitClient.getRetrofitClient()?.create(AuthService::class.java)
    }
}