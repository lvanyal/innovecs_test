package com.example.myapplication.domain

data class ActionConfig(
    val coolDown: Int,
    val enabled: Boolean,
    val priority: Int,
    val type: String,
    val validDays: List<Int>
)