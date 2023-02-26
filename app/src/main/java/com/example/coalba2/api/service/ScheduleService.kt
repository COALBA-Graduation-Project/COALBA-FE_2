package com.example.coalba2.api.service

import com.example.coalba2.data.request.ScheduleAddData
import com.example.coalba2.data.response.*
import retrofit2.Call
import retrofit2.http.*

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

    // 해당 날짜에 근무 가능한 해당 워크스페이스 내 알바 리스트 조회 (for 스케줄 추가)
    @GET("boss/schedules/possible/staffs")
    fun schedulePossible(@Query("workspaceId") workspaceId: Long, @Query("start") start: String, @Query("end") end: String) : Call<SchedulePossibleResponseData>

    // 스케줄 생성
    @POST("boss/schedules")
    fun scheduleAdd(@Body data : ScheduleAddData) : Call<Void>

    // 스케줄 삭제
    @DELETE("boss/schedules/{scheduleId}")
    fun scheduleDelete(@Path("scheduleId") scheduleId: Long) : Call<Void>

    // 근무내역 및 알바비 관리 년월 리스트 조회
    @GET("boss/schedules/reports/dates")
    fun workhistoryDate(@Query("workspaceId") workspaceId: Long) : Call<WorkHistoryDateResponseData>

    // 해당 가게, 해당 년월 근무내역 및 알바비 관리 리스트 조회
    @GET("boss/schedules/reports")
    fun workhistoryList(@Query("workspaceId") workspaceId: Long, @Query("year") year: Int, @Query("month") month: Int) : Call<WorkHistoryListResponseData>
}