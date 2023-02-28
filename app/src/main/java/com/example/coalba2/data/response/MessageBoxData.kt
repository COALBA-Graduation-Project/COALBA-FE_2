package com.example.coalba2.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MessageBoxData(
    @SerializedName("img")
    val img: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("latestMessage")
    val latestMessage: String,
    @SerializedName("latestDateTime")
    val latestDateTime: String
):Parcelable