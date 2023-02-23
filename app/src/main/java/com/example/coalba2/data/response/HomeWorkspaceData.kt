package com.example.coalba2.data.response

data class HomeWorkspaceData(
    val img : String,
    val name : String,
    val staffList : MutableList<HomeWorkspaceStaffData>
)
