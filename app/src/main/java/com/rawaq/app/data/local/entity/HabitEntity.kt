package com.rawaq.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "habits",
    indices = [
        Index(value = ["category"], name = "index_habits_category"),
        Index(value = ["is_archived"], name = "index_habits_is_archived")
    ]
)
data class HabitEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String?,
    val category: String,
    @ColumnInfo(name = "target_type")
    val targetType: String,
    @ColumnInfo(name = "target_value")
    val targetValue: Double,
    val unit: String?,
    @ColumnInfo(name = "color_hex")
    val colorHex: String?,
    @ColumnInfo(name = "icon_name")
    val iconName: String?,
    @ColumnInfo(name = "is_archived")
    val isArchived: Boolean,
    @ColumnInfo(name = "archived_at_epoch_day")
    val archivedAtEpochDay: Int?,
    @ColumnInfo(name = "exclude_from_analytics")
    val excludeFromAnalytics: Boolean,
    @ColumnInfo(name = "sort_order")
    val sortOrder: Int,
    @ColumnInfo(name = "created_at_timestamp")
    val createdAtTimestamp: Long,
    @ColumnInfo(name = "updated_at_timestamp")
    val updatedAtTimestamp: Long
)
