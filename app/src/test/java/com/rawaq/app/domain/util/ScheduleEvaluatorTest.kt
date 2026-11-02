package com.rawaq.app.domain.util

import com.rawaq.app.domain.model.HabitScheduleRule
import com.rawaq.app.domain.model.ScheduleType
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.DayOfWeek
import java.time.LocalDate

class ScheduleEvaluatorTest {

    @Test
    fun dailySchedule_isDueEverydayWithinValidity() {
        val rule = HabitScheduleRule(
            id = "rule1",
            habitId = "h1",
            scheduleType = ScheduleType.DAILY,
            validFromEpochDay = 100,
            validUntilEpochDay = 200
        )

        assertTrue(ScheduleEvaluator.isDue(rule, 100))
        assertTrue(ScheduleEvaluator.isDue(rule, 150))
        assertTrue(ScheduleEvaluator.isDue(rule, 200))
        assertFalse(ScheduleEvaluator.isDue(rule, 99))
        assertFalse(ScheduleEvaluator.isDue(rule, 201))
    }

    @Test
    fun weeklyDaysSchedule_evaluatesBitmaskCorrectly() {
        // Mon (1), Wed (4), Fri (16) -> Mask = 21
        val mask = ScheduleEvaluator.createDaysOfWeekMask(
            setOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)
        )

        val mondayDate = LocalDate.of(2023, 11, 6) // Monday
        val tuesdayDate = LocalDate.of(2023, 11, 7) // Tuesday
        val wednesdayDate = LocalDate.of(2023, 11, 8) // Wednesday

        val rule = HabitScheduleRule(
            id = "rule2",
            habitId = "h1",
            scheduleType = ScheduleType.WEEKLY_DAYS,
            daysOfWeekMask = mask,
            validFromEpochDay = mondayDate.toEpochDay().toInt()
        )

        assertTrue(ScheduleEvaluator.isDue(rule, mondayDate.toEpochDay().toInt()))
        assertFalse(ScheduleEvaluator.isDue(rule, tuesdayDate.toEpochDay().toInt()))
        assertTrue(ScheduleEvaluator.isDue(rule, wednesdayDate.toEpochDay().toInt()))
    }

    @Test
    fun pausedSchedule_isNeverDue() {
        val rule = HabitScheduleRule(
            id = "rule3",
            habitId = "h1",
            scheduleType = ScheduleType.PAUSED,
            validFromEpochDay = 100
        )

        assertFalse(ScheduleEvaluator.isDue(rule, 100))
        assertFalse(ScheduleEvaluator.isDue(rule, 150))
    }
}
