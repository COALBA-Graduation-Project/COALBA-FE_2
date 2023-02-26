package com.example.coalba2.api.retrofit

import com.example.coalba2.api.service.AuthService
import com.example.coalba2.api.service.ProfileService
import com.example.coalba2.api.service.ScheduleService
import com.example.coalba2.api.service.WorkspaceService

class RetrofitManager {
    companion object {
        val authService = RetrofitClient.getRetrofitClient()?.create(AuthService::class.java)
        val profileService = RetrofitClient.getRetrofitClient()?.create(ProfileService::class.java)
        val workspaceService = RetrofitClient.getRetrofitClient()?.create(WorkspaceService::class.java)
        val scheduleService = RetrofitClient.getRetrofitClient()?.create(ScheduleService::class.java)
    }
}