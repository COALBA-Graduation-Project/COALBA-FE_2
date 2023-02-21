package com.example.coalba2.api.service

import com.example.coalba2.data.response.WorkspaceBriefListLookResponseData
import com.example.coalba2.data.response.WorkspaceListLookResponseData
import com.example.coalba2.data.response.WorkspaceStaffListLookResponseData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface WorkspaceService {
    // 워크스페이스 관련 service
    // 나의 워크스페이스 리스트 조회
    @GET("boss/workspaces")
    fun workspaceListLook() : Call<WorkspaceListLookResponseData>

    // 나의 워크스페이스 요약 리스트 조회
    @GET("boss/workspaces/brief")
    fun workspaceBriefListLook() : Call<WorkspaceBriefListLookResponseData>
    // 해당 워크스페이스 정보 상세 조회 => 아직 (워크스페이스 수정 화면에서 사용)


    // 해당 워크스페이스 내 알바 정보 리스트 조회
    @GET("boss/workspaces/{workspaceId}/staffs")
    fun workspaceStaffListLook(@Path("workspaceId") workspaceId: Long) : Call<WorkspaceStaffListLookResponseData>

    // 워크스페이스 추가
    @Multipart
    @POST("boss/workspaces")
    fun workspaceAdd(@Part("workspace") WorkspaceAddData: RequestBody, @Part imageFile: MultipartBody.Part): Call<Void>

    // 해당 워크스페이스에 알바 추가
    @POST("boss/workspaces/{workspaceId}/staffs")
    fun workspaceStaffAdd(@Path("workspaceId") workspaceId: Long, @Query("email") email: String): Call<Void>
}