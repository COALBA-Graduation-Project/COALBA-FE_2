package com.example.coalba2.data.response

data class MessageBoxData(
    // 아마 이미지는 string 값으로 넘길듯
    val img: Int,
    val name: String,
    val latestMessage: String,
    val latestDateTime: String
)