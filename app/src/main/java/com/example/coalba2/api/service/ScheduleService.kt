package com.example.coalba2.api.service

import com.example.coalba2.data.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ScheduleService {
    // 스케줄 관련 service
    // 홈 달력 정보 조회 & 오늘 스케줄 조회
    @GET("boss/schedules/home")
    fun scheduleMain() : Call<ScheduleMainResponseData>

    // 홈 가게별 해당 날짜 스케줄 조회
    @GET("boss/schedules/home/selected")
    fun scheduleEachWorkspace(@Query("year") year: Int, @Query("month") month: Int, @Query("day") day: Int) : Call<ScheduleEachWorkspaceResponseData>

    // 해당 워크스페이스 홈 달력 정보 조회
    @GET("boss/schedules")
    fun scheduleCalendar(@Query("workspaceId") workspaceId: Long) : Call<ScheduleCalendarResponseData>

    // 해당 워크스페이스 홈 해당 날짜 스케줄 조회
    @GET("boss/schedules/selected")
    fun scheduleEachWorkspaceSchedule(@Query("workspaceId") workspaceId: Long, @Query("year") year: Int, @Query("month") month: Int, @Query("day") day: Int) : Call<ScheduleEachWorkspaceScheduleResponseData>

    // 스케줄 생성 => 아직
    @POST("boss/schedules")
    fun scheduleAdd() : Call<Void>
}