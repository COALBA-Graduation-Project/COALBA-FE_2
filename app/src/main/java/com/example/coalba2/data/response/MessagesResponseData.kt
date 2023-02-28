package com.example.coalba2.data.response

import com.google.gson.annotations.SerializedName

data class MessagesResponseData(
    @SerializedName("workspaceId")
    val workspaceId: Long,
    @SerializedName("staffId")
    val staffId: Long,
    @SerializedName("staffName")
    val staffName: String,
    @SerializedName("staffImageUrl")
    val staffImageUrl: String,
    @SerializedName("messageList")
    var messageList: List<MessageListData> = arrayListOf()
)
data class MessageListData(
    @SerializedName("messagId")
    val messagId: Long,
    @SerializedName("criteria")
    val criteria: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("createdDate")
    val createdDate: String
)
