package com.example.coalba2.api.service

import com.example.coalba2.data.request.MessageSendData
import com.example.coalba2.data.request.ScheduleAddData
import com.example.coalba2.data.response.MessageBoxResponseData
import com.example.coalba2.data.response.MessageSendResponseData
import com.example.coalba2.data.response.MessagesResponseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MessageService {
    // 메시지 관련 service
    // 해당 워크스페이스 내 알바와의 쪽지함 리스트 조회
    @GET("boss/messages/boxes")
    fun messageBox(@Query("workspaceId") workspaceId: Long) : Call<MessageBoxResponseData>

    // 해당 워크스페이스의 해당 알바 쪽지함 내 메시지 리스트 조회 (최신순)
    @GET("boss/messages")
    fun messages(@Query("workspaceId") workspaceId: Long, @Query("staffId") staffId: Long) : Call<MessagesResponseData>

    // 해당 워크스페이스의 해당 알바에게 쪽지 보내기
    @POST("boss/messages")
    fun messageSend(@Query("workspaceId") workspaceId: Long, @Query("staffId") staffId: Long, @Body data : MessageSendData) : Call<MessagesResponseData>
}