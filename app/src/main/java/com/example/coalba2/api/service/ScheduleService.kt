package com.example.coalba2.api.service

import com.example.coalba2.data.response.ScheduleMainResponseData
import com.example.coalba2.data.response.WorkspaceListLookResponseData
import retrofit2.Call
import retrofit2.http.GET

interface ScheduleService {
    // 스케줄 관련 service
    // 홈 달력 정보 조회 & 오늘 스케줄 조회
    @GET("boss/schedules/home")
    fun scheduleMain() : Call<ScheduleMainResponseData>
}