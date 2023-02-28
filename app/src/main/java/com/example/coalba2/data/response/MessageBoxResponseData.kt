package com.example.coalba2.data.response

import com.google.gson.annotations.SerializedName

data class MessageBoxResponseData(
    @SerializedName("messageBoxList")
    var messageBoxList: List<MessageBoxListData> = arrayListOf()
)
data class MessageBoxListData(
    @SerializedName("staff")
    val staff: MessageStaffData?,
    @SerializedName("latestMessage")
    val latestMessage: String,
    @SerializedName("latestDateTime")
    val latestDateTime: String
)
data class MessageStaffData(
    @SerializedName("staffId")
    val staffId: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("imageUrl")
    val imageUrl: String
)
