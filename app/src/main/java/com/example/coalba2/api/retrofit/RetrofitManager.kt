package com.example.coalba2.api.retrofit

import com.example.coalba2.api.service.*

class RetrofitManager {
    companion object {
        val authService = RetrofitClient.getRetrofitClient()?.create(AuthService::class.java)
        val profileService = RetrofitClient.getRetrofitClient()?.create(ProfileService::class.java)
        val workspaceService = RetrofitClient.getRetrofitClient()?.create(WorkspaceService::class.java)
        val scheduleService = RetrofitClient.getRetrofitClient()?.create(ScheduleService::class.java)
        val messageService = RetrofitClient.getRetrofitClient()?.create(MessageService::class.java)
        val notificationService = RetrofitClient.getRetrofitClient()?.create(NotificationService::class.java)
    }
}