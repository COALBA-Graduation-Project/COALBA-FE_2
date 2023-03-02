package com.example.coalba2.data.response

import com.google.gson.annotations.SerializedName

data class SubstituteReqDetailResponseData(
    @SerializedName("substituteReqId")
    val substituteReqId: Long,
    @SerializedName("reqMessage")
    val reqMessage: String,
    @SerializedName("status")
    val status: String,

    @SerializedName("senderId")
    val senderId: Long,
    @SerializedName("senderImageUrl")
    val senderImageUrl: String,
    @SerializedName("senderName")
    val senderName: String,
    @SerializedName("receiverId")
    val receiverId: Long,
    @SerializedName("receiverImageUrl")
    val receiverImageUrl: String,
    @SerializedName("receiverName")
    val receiverName: String,

    @SerializedName("workspaceId")
    val workspaceId: Long,
    @SerializedName("workspaceName")
    val workspaceName: String,

    @SerializedName("startDateTime")
    val startDateTime: String,
    @SerializedName("endDateTime")
    val endDateTime: String
)
