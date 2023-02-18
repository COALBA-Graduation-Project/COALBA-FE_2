package com.example.coalba2.api.service

import com.example.coalba2.data.response.ProfileLookResponseData
import com.example.coalba2.data.response.WorkspaceListLookResponseData
import retrofit2.Call
import retrofit2.http.GET

interface WorkspaceService {
    // 워크스페이스 관련 service
    // 나의 워크스페이스 리스트 조회
    @GET("boss/workspaces")
    fun workspaceListLook() : Call<WorkspaceListLookResponseData>
}