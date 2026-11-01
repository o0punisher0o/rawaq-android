package com.rawaq.app.data.model

data class DailyProgress(
    val completedCount: Int,
    val totalCount: Int
) {
    val progressFraction: Float
        get() = if (totalCount > 0) completedCount.toFloat() / totalCount else 0f
}
