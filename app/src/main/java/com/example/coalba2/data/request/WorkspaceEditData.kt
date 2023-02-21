package com.example.coalba2.data.request

import com.google.gson.annotations.SerializedName

data class WorkspaceEditData(
    @SerializedName("workspace")
    val workspace: StoreEditData?
)
data class StoreEditData(
    @SerializedName("name")
    val name: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("prevImageUrl")
    val prevImageUrl: String
)
