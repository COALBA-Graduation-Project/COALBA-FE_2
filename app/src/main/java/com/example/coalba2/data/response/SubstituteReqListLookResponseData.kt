package com.example.coalba2.data.response

import com.google.gson.annotations.SerializedName

data class SubstituteReqListLookResponseData(
    @SerializedName("totalSubstituteReqList")
    var totalSubstituteReqList: List<SubstituteReqListOfMonthData> = arrayListOf()
)

data class SubstituteReqListOfMonthData(
    @SerializedName("year")
    val year: Int,
    @SerializedName("month")
    val month: Int,
    @SerializedName("substituteReqList")
    var substituteReqList: List<SubstituteReqListData> = arrayListOf()
)

data class SubstituteReqListData(
    @SerializedName("substituteReqId")
    val substituteReqId: Long,

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
    val endDateTime: String,
    @SerializedName("status")
    val status: String
)
