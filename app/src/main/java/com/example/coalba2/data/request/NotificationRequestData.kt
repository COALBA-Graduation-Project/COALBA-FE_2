package com.example.coalba2.data.request

import com.google.gson.annotations.SerializedName

data class NotificationRequestData(
    @SerializedName("deviceToken")
    val deviceToken: String
)
