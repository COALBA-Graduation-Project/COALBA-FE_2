package com.example.coalba2.data.response

import com.google.gson.annotations.SerializedName

data class WorkspaceStaffListLookResponseData(
    @SerializedName("staffInfoList")
    var staffInfoList: List<StaffInfoListData> = arrayListOf()
)
data class StaffInfoListData(
    @SerializedName("staffId")
    val staffId: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("birthDate")
    val birthDate: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("workGrade")
    val workGrade: Int
)
