package com.example.coalba2.data.response

import com.google.gson.annotations.SerializedName

data class ScheduleMainResponseData(
    @SerializedName("dateList")
    var dateList: List<ScheduleDateListData> = arrayListOf(),
    @SerializedName("selectedSubPage")
    val selectedSubPage: WorkspaceListOfData?
)
data class ScheduleDateListData(
    @SerializedName("date")
    val date: DateData?,
    @SerializedName("totalScheduleStatus")
    val totalScheduleStatus: String
)
data class DateData(
    @SerializedName("year")
    val year: Int,
    @SerializedName("month")
    val month: Int,
    @SerializedName("day")
    val day: Int,
    @SerializedName("dayOfWeek")
    val dayOfWeek: String
)
data class WorkspaceListOfData(
    @SerializedName("selectedDate")
    val selectedDate: SelectedDateData?,
    @SerializedName("workspaceList")
    var workspaceList: List<ScheduleWorkspaceListData> = arrayListOf()
)
data class SelectedDateData(
    @SerializedName("year")
    val year: Int,
    @SerializedName("month")
    val month: Int,
    @SerializedName("day")
    val day: Int,
    @SerializedName("dayOfWeek")
    val dayOfWeek: String
)
data class ScheduleWorkspaceListData(
    @SerializedName("workspaceId")
    val workspaceId: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("selectedScheduleList")
    var selectedScheduleList: List<ScheduleListOfWorkspaceData> = arrayListOf()
)
data class ScheduleListOfWorkspaceData(
    @SerializedName("scheduleId")
    val scheduleId: Long,
    @SerializedName("scheduleStartTime")
    val scheduleStartTime: String,
    @SerializedName("scheduleEndTime")
    val scheduleEndTime: String,
    @SerializedName("logicalStartTime")
    val logicalStartTime: String,
    @SerializedName("logicalEndTime")
    val logicalEndTime: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("worker")
    val worker: WorkerScheduleData?
)
data class WorkerScheduleData(
    @SerializedName("workerId")
    val workerId: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("imageUrl")
    val imageUrl: String
)
