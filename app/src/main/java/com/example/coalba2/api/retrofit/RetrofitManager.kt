package com.example.coalba2.api.retrofit

import com.example.coalba2.api.service.AuthService
import com.example.coalba2.api.service.ProfileService

class RetrofitManager {
    companion object {
        val authService = RetrofitClient.getRetrofitClient()?.create(AuthService::class.java)
        val profileService = RetrofitClient.getRetrofitClient()?.create(ProfileService::class.java)
    }
}