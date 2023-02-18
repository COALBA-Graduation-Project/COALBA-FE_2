package com.example.coalba2.data.response

import com.google.gson.annotations.SerializedName

data class WorkspaceListLookResponseData(
    @SerializedName("workspaceList")
    var workspaceList: List<WorkspaceListData> = arrayListOf()
)
data class WorkspaceListData(
    @SerializedName("workspaceId")
    val workspaceId: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("imageUrl")
    val imageUrl: String
)
