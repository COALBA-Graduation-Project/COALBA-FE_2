package com.example.coalba2.data.response

import com.google.gson.annotations.SerializedName

data class ScheduleEachWorkspaceResponseData(
    @SerializedName("selectedDate")
    val selectedDate: SelectedDateEachData?,
    @SerializedName("selectedWorkspaceId")
    val selectedWorkspaceId: Long,
    @SerializedName("selectedScheduleList")
    var selectedScheduleList: List<ScheduleDateEachListData> = arrayListOf()
)
data class SelectedDateEachData(
    @SerializedName("year")
    val year: Int,
    @SerializedName("month")
    val month: Int,
    @SerializedName("day")
    val day: Int,
    @SerializedName("dayOfWeek")
    val dayOfWeek: String
)
data class ScheduleDateEachListData(
    @SerializedName("scheduleId")
    val scheduleId: Long,
    @SerializedName("scheduleStartTime")
    val scheduleStartTime: String,
    @SerializedName("scheduleEndTime")
    val scheduleEndTime: String,
    @SerializedName("logicalStartTime")
    val logicalStartTime: String?,
    @SerializedName("logicalEndTime")
    val logicalEndTime: String?,
    @SerializedName("status")
    val status: String,
    @SerializedName("worker")
    val worker: WorkerData?
)
data class WorkerData(
    @SerializedName("workerId")
    val workerId: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("imageUrl")
    val imageUrl: String
)
