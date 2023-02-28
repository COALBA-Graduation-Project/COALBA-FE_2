package com.example.coalba2.data.response

import com.google.gson.annotations.SerializedName

data class WorkHistoryDateResponseData(
    @SerializedName("dateList")
    var dateList: List<WorkHistoryDateData> = arrayListOf()
)
data class WorkHistoryDateData(
    @SerializedName("year")
    val year: Int,
    @SerializedName("month")
    val month: Int
)

