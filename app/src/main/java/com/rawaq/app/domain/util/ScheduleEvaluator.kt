package com.rawaq.app.domain.util

import com.rawaq.app.domain.model.HabitScheduleRule
import com.rawaq.app.domain.model.ScheduleType
import java.time.DayOfWeek
import java.time.LocalDate

object ScheduleEvaluator {

    fun isDue(rule: HabitScheduleRule, logicalEpochDay: Int): Boolean {
        if (logicalEpochDay < rule.validFromEpochDay) return false
        if (rule.validUntilEpochDay != null && logicalEpochDay > rule.validUntilEpochDay) return false

        return when (rule.scheduleType) {
            ScheduleType.DAILY -> true
            ScheduleType.PAUSED -> false
            ScheduleType.WEEKLY_DAYS -> {
                val mask = rule.daysOfWeekMask ?: return false
                val date = LocalDate.ofEpochDay(logicalEpochDay.toLong())
                isDayOfWeekSelected(mask, date.dayOfWeek)
            }
        }
    }

    fun isDayOfWeekSelected(mask: Int, dayOfWeek: DayOfWeek): Boolean {
        val shift = dayOfWeek.value - 1
        return (mask and (1 shl shift)) != 0
    }

    fun createDaysOfWeekMask(days: Set<DayOfWeek>): Int {
        var mask = 0
        for (day in days) {
            val shift = day.value - 1
            mask = mask or (1 shl shift)
        }
        return mask
    }
}
