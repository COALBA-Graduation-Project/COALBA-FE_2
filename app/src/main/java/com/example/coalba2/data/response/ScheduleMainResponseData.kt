package com.example.coalba2.data.response

import com.google.gson.annotations.SerializedName

data class ScheduleMainResponseData(
    @SerializedName("dateList")
    var dateList: List<ScheduleDateListData> = arrayListOf(),
    @SerializedName("selectedDate")
    val selectedDate: SelectedDateData?,
    @SerializedName("workspaceList")
    var workspaceList: List<ScheduleWorkspaceListData> = arrayListOf()
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
    val imageUrl: String
)
