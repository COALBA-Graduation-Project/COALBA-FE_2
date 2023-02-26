package com.example.coalba2.data.response

import com.google.gson.annotations.SerializedName

data class SchedulePossibleResponseData(
    @SerializedName("staffList")
    var staffList: List<StaffListData> = arrayListOf()
)
data class StaffListData(
    @SerializedName("staffId")
    val staffId: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("imageUrl")
    val imageUrl: String
)
