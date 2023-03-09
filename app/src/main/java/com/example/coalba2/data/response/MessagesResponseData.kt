package com.example.coalba2.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
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
): Parcelable
@Parcelize
data class MessageListData(
    @SerializedName("messageId")
    val messageId: Long,
    @SerializedName("sendingOrReceiving")
    val sendingOrReceiving: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("createDate")
    val createDate: String
): Parcelable
