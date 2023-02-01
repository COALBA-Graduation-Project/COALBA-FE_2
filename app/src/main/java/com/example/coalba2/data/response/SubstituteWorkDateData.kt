package com.example.coalba2.data.response

data class SubstituteWorkDateData(
    val date: String,
    val innerList: MutableList<SubstituteWorkScheduleData>
)
