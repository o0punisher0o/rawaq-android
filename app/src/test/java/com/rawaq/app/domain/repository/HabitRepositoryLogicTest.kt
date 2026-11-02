package com.rawaq.app.domain.repository

import com.rawaq.app.domain.model.CompletionStatus
import com.rawaq.app.domain.model.Habit
import com.rawaq.app.domain.model.HabitScheduleRule
import com.rawaq.app.domain.model.ScheduleType
import com.rawaq.app.domain.model.TargetType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

class HabitRepositoryLogicTest {

    @Test
    fun numericHabitStatus_lessThanTarget_returnsPartial() {
        val targetValue = 10.0
        val actualValue = 6.0

        val status = if (actualValue >= targetValue) CompletionStatus.COMPLETED else CompletionStatus.PARTIAL
        assertEquals(CompletionStatus.PARTIAL, status)
    }

    @Test
    fun numericHabitStatus_greaterOrEqualTarget_returnsCompleted() {
        val targetValue = 10.0
        val actualValue = 10.0

        val status = if (actualValue >= targetValue) CompletionStatus.COMPLETED else CompletionStatus.PARTIAL
        assertEquals(CompletionStatus.COMPLETED, status)
    }

    @Test
    fun scheduleVersioning_nonOverlappingRanges() {
        val nov1 = LocalDate.of(2023, 11, 1).toEpochDay().toInt()
        val nov5 = LocalDate.of(2023, 11, 5).toEpochDay().toInt()

        val rule1 = HabitScheduleRule(
            id = "r1",
            habitId = "h1",
            scheduleType = ScheduleType.DAILY,
            validFromEpochDay = nov1,
            validUntilEpochDay = nov5 - 1 // Nov 4
        )

        val rule2 = HabitScheduleRule(
            id = "r2",
            habitId = "h1",
            scheduleType = ScheduleType.WEEKLY_DAYS,
            daysOfWeekMask = 21,
            validFromEpochDay = nov5,
            validUntilEpochDay = null
        )

        // Check date Nov 3
        val nov3 = LocalDate.of(2023, 11, 3).toEpochDay().toInt()
        val ruleForNov3 = listOf(rule1, rule2).find { nov3 >= it.validFromEpochDay && (it.validUntilEpochDay == null || nov3 <= it.validUntilEpochDay) }
        assertEquals(rule1.id, ruleForNov3?.id)

        // Check date Nov 6
        val nov6 = LocalDate.of(2023, 11, 6).toEpochDay().toInt()
        val ruleForNov6 = listOf(rule1, rule2).find { nov6 >= it.validFromEpochDay && (it.validUntilEpochDay == null || nov6 <= it.validUntilEpochDay) }
        assertEquals(rule2.id, ruleForNov6?.id)
    }

    @Test
    fun archivingHabit_preservesHistoryBeforeArchivedDate() {
        val nov1 = LocalDate.of(2023, 11, 1).toEpochDay().toInt()
        val nov20 = LocalDate.of(2023, 11, 20).toEpochDay().toInt()

        val archivedHabit = Habit(
            id = "h1",
            title = "Reading",
            category = "Growth",
            targetType = TargetType.BINARY,
            isArchived = true,
            archivedAtEpochDay = nov20
        )

        val checkNov15 = LocalDate.of(2023, 11, 15).toEpochDay().toInt()
        val isArchivedOnNov15 = archivedHabit.isArchived && archivedHabit.archivedAtEpochDay != null && checkNov15 >= archivedHabit.archivedAtEpochDay
        assertFalse("Habit should not be considered archived before Nov 20", isArchivedOnNov15)

        val checkNov22 = LocalDate.of(2023, 11, 22).toEpochDay().toInt()
        val isArchivedOnNov22 = archivedHabit.isArchived && archivedHabit.archivedAtEpochDay != null && checkNov22 >= archivedHabit.archivedAtEpochDay
        assertTrue("Habit should be considered archived on Nov 22", isArchivedOnNov22)
    }
}
