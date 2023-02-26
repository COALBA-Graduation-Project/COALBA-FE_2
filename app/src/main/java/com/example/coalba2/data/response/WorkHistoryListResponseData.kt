package com.example.coalba2.data.response

import com.google.gson.annotations.SerializedName

data class WorkHistoryListResponseData(
    @SerializedName("selectedWorkspaceId")
    val selectedWorkspaceId: Long,
    @SerializedName("selectedYear")
    val selectedYear: Int,
    @SerializedName("selectedMonth")
    val selectedMonth: Int,
    @SerializedName("workReportList")
    var workReportList: List<WorkHistoryListData> = arrayListOf()
)
data class WorkHistoryListData(
    @SerializedName("worker")
    val worker: WorkerWorkHistoryData?,
    @SerializedName("totalWorkTimeHour")
    val totalWorkTimeHour: Int,
    @SerializedName("totalWorkTimeMin")
    val totalWorkTimeMin: Int,
    @SerializedName("totalWorkPay")
    val totalWorkPay: String
)
data class WorkerWorkHistoryData(
    @SerializedName("workerId")
    val workerId: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("imageUrl")
    val imageUrl: String
)