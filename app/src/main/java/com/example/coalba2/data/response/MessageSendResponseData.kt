package com.example.coalba2.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MessageSendResponseData(
    @SerializedName("messageId")
    val messageId: Long,
    @SerializedName("sendingOrReceiving")
    val sendingOrReceiving: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("createDate")
    val createDate: String
): Parcelable
