package com.example.coalba2.data.response

data class ScheduleData(
    val scheduleId : Long,
    val name : String,
    val starttime : String,
    val endtime : String,
    val status : String
)