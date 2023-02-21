package com.example.coalba2.data.request

import com.google.gson.annotations.SerializedName

data class WorkspaceAddData(
    @SerializedName("workspace")
    val workspace: AddData?
)
data class AddData(
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
    val payType: String
)
