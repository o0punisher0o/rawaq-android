package com.rawaq.app.data.model

data class Habit(
    val id: String,
    val title: String,
    val category: String,
    val isCompletedToday: Boolean = false,
    val streakDays: Int = 0
)
