package com.example.coalba2.api.service

import com.example.coalba2.data.response.ProfileLookResponseData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ProfileService {
    // 프로필 관련 service
    // 프로필 수정 => 아직
    @Multipart
    @POST("boss/profile")
    fun profileRegister(@Part("profile") ProfileRegisterData: RequestBody, @Part imageFile: MultipartBody.Part): Call<Void>

    // 프로필 조회 => 아직
    @GET("boss/profile")
    fun profileLook() : Call<ProfileLookResponseData>
}