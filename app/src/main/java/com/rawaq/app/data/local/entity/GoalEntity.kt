package com.rawaq.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "goals",
    indices = [
        Index(value = ["status"], name = "index_goals_status")
    ]
)
data class GoalEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String?,
    val category: String?,
    @ColumnInfo(name = "target_date_epoch_day")
    val targetDateEpochDay: Int?,
    @ColumnInfo(name = "target_value")
    val targetValue: Double,
    @ColumnInfo(name = "current_value")
    val currentValue: Double,
    val unit: String?,
    val status: String,
    @ColumnInfo(name = "created_at_timestamp")
    val createdAtTimestamp: Long,
    @ColumnInfo(name = "updated_at_timestamp")
    val updatedAtTimestamp: Long
)
