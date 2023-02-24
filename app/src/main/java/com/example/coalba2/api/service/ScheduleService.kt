package com.example.coalba2.api.service

import com.example.coalba2.data.response.ScheduleCalendarResponseData
import com.example.coalba2.data.response.ScheduleEachWorkspaceResponseData
import com.example.coalba2.data.response.ScheduleMainResponseData
import com.example.coalba2.data.response.WorkspaceListLookResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ScheduleService {
    // 스케줄 관련 service
    // 홈 달력 정보 조회 & 오늘 스케줄 조회 => 아직
    @GET("boss/schedules/home")
    fun scheduleMain() : Call<ScheduleMainResponseData>

    // 홈 가게별 해당 날짜 스케줄 조회 => 아직
    @GET("boss/schedules/home/selected")
    fun scheduleEachWorkspace(@Query("workspaceId") workspaceId: Long, @Query("year") year: Int, @Query("month") month: Int, @Query("day") day: Int) : Call<ScheduleEachWorkspaceResponseData>

    // 해당 워크스페이스 홈 달력 정보 조회
    @GET("boss/schedules/home")
    fun scheduleCalendar(@Query("workspaceId") workspaceId: Long) : Call<ScheduleCalendarResponseData>
}