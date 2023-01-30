package com.example.coalba2.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MessageBoxData(
    // 아마 이미지는 string 값으로 넘길듯
    @SerializedName("img")
    val img: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("latestMessage")
    val latestMessage: String,
    @SerializedName("latestDateTime")
    val latestDateTime: String
):Parcelable