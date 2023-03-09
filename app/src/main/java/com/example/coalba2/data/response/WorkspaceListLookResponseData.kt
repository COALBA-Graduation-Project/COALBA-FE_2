package com.example.coalba2.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WorkspaceListLookResponseData(
    @SerializedName("workspaceList")
    var workspaceList: List<WorkspaceListData> = arrayListOf()
): Parcelable

@Parcelize
data class WorkspaceListData(
    @SerializedName("workspaceId")
    val workspaceId: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("imageUrl")
    val imageUrl: String
): Parcelable
