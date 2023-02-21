package com.example.coalba2.data.response

import com.google.gson.annotations.SerializedName

data class WorkspaceDetailResponseData(
    @SerializedName("workspaceId")
    val workspaceId: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("businessNumber")
    val businessNumber: String,
    @SerializedName("workType")
    val workType: String,
    @SerializedName("payType")
    val payType: String,
    @SerializedName("imageUrl")
    val imageUrl: String
)
