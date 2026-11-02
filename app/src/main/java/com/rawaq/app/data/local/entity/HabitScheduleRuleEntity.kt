package com.rawaq.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "habit_schedule_rules",
    foreignKeys = [
        ForeignKey(
            entity = HabitEntity::class,
            parentColumns = ["id"],
            childColumns = ["habit_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["habit_id", "valid_from_epoch_day"], name = "index_schedule_rules_habit_validity")
    ]
)
data class HabitScheduleRuleEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "habit_id")
    val habitId: String,
    @ColumnInfo(name = "schedule_type")
    val scheduleType: String,
    @ColumnInfo(name = "days_of_week_mask")
    val daysOfWeekMask: Int?,
    @ColumnInfo(name = "valid_from_epoch_day")
    val validFromEpochDay: Int,
    @ColumnInfo(name = "valid_until_epoch_day")
    val validUntilEpochDay: Int?,
    @ColumnInfo(name = "created_at_timestamp")
    val createdAtTimestamp: Long
)
