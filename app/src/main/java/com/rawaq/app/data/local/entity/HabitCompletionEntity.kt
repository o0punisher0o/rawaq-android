package com.rawaq.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "habit_completions",
    foreignKeys = [
        ForeignKey(
            entity = HabitEntity::class,
            parentColumns = ["id"],
            childColumns = ["habit_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["habit_id", "logical_date"], unique = true, name = "index_completions_habit_date_unique"),
        Index(value = ["logical_date"], name = "index_completions_logical_date")
    ]
)
data class HabitCompletionEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "habit_id")
    val habitId: String,
    @ColumnInfo(name = "logical_date")
    val logicalDate: Int,
    val status: String,
    @ColumnInfo(name = "numeric_value")
    val numericValue: Double?,
    @ColumnInfo(name = "completed_at_timestamp")
    val completedAtTimestamp: Long,
    val note: String?,
    @ColumnInfo(name = "updated_at_timestamp")
    val updatedAtTimestamp: Long
)
