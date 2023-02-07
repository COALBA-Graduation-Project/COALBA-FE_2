package com.example.coalba2.api.service

import com.example.coalba2.data.request.ProfileRegisterData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ProfileService {
    // 프로필 관련 service

    // 프로필 수정
    @Multipart
    @POST("boss/profile")
    fun profileRegister(@Part("profile") ProfileRegisterData: RequestBody, @Part imageUrl: MultipartBody.Part): Call<Void>
}