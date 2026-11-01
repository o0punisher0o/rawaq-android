package com.rawaq.app.data.model

data class Goal(
    val id: String,
    val title: String,
    val description: String,
    val progressPercent: Float, // 0.0f to 1.0f
    val targetDate: String
)
