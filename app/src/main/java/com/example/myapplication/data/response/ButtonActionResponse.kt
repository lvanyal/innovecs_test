package com.example.myapplication.data.response

data class ButtonActionResponse(
    val cool_down: Int,
    val enabled: Boolean,
    val priority: Int,
    val type: String,
    val valid_days: List<Int>
)