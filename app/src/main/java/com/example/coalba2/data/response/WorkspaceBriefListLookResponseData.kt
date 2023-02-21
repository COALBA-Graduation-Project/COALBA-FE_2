package com.example.coalba2.data.response

import com.google.gson.annotations.SerializedName

data class WorkspaceBriefListLookResponseData(
    @SerializedName("workspaceList")
    var workspaceList: List<WorkspaceBriefListData> = arrayListOf()
)
data class WorkspaceBriefListData(
    @SerializedName("workspaceId")
    val workspaceId: Long,
    @SerializedName("name")
    val name: String
)
