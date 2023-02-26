package com.example.coalba2.data.request

import com.example.coalba2.data.response.SelectedWorkspaceData
import com.google.gson.annotations.SerializedName

data class ScheduleAddData(
    @SerializedName("workspaceId")
    val workspaceId: Long,
    @SerializedName("staffId")
    val staffId: Long,
    @SerializedName("scheduleDateTime")
    val scheduleDateTime: ScheduleDateData?,
    @SerializedName("hourlyWage")
    val hourlyWage: Int
)
data class ScheduleDateData(
    @SerializedName("start")
    val start: String,
    @SerializedName("end")
    val end: String
)
