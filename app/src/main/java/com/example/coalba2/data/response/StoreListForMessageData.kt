package com.example.coalba2.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StoreListForMessageData(
    @SerializedName("img")
    val img : Int,
    @SerializedName("name")
    val name : String) : Parcelable
