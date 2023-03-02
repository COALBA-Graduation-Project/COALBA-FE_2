package com.example.coalba2.api.service

import com.example.coalba2.data.response.SubstituteReqDetailResponseData
import com.example.coalba2.data.response.SubstituteReqListLookResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface SubstituteReqService {
    // 대타요청 관련 service
    // 대타근무 요청 관리 리스트 조회
    @GET("boss/substituteReqs")
    fun substituteReqListLook() : Call<SubstituteReqListLookResponseData>

    // 대타근무 요청 관리 상세 조회
    @GET("boss/substituteReqs/{substituteReqId}")
    fun substituteReqDetail(@Path("substituteReqId") substituteReqId: Long) : Call<SubstituteReqDetailResponseData>

    // 대타근무 요청 수락
    @PUT("boss/substituteReqs/{substituteReqId}/accept")
    fun substituteReqAccept(@Path("substituteReqId") substituteReqId: Long) : Call<Void>

    // 대타근무 요청 거절
    @PUT("boss/substituteReqs/{substituteReqId}/reject")
    fun substituteReqReject(@Path("substituteReqId") substituteReqId: Long) : Call<Void>
}