package com.example.coalba2.data.request

import com.google.gson.annotations.SerializedName

data class MessageSendData(
    @SerializedName("content")
    val content: String
)
