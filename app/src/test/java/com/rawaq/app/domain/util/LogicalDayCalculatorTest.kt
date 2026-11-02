package com.rawaq.app.domain.util

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class LogicalDayCalculatorTest {

    @Test
    fun getLogicalEpochDay_at0259With180MinOffset_returnsPreviousLogicalDay() {
        // 2023-11-02 02:59:59 UTC
        val instant = Instant.parse("2023-11-02T02:59:59Z")
        val zoneId = ZoneId.of("UTC")
        val offsetMinutes = 180 // 3 hours

        val logicalEpochDay = LogicalDayCalculator.getLogicalEpochDay(instant, zoneId, offsetMinutes)
        val logicalDate = LocalDate.ofEpochDay(logicalEpochDay.toLong())

        assertEquals(LocalDate.parse("2023-11-01"), logicalDate)
    }

    @Test
    fun getLogicalEpochDay_at0300With180MinOffset_returnsCurrentLogicalDay() {
        // 2023-11-02 03:00:00 UTC
        val instant = Instant.parse("2023-11-02T03:00:00Z")
        val zoneId = ZoneId.of("UTC")
        val offsetMinutes = 180 // 3 hours

        val logicalEpochDay = LogicalDayCalculator.getLogicalEpochDay(instant, zoneId, offsetMinutes)
        val logicalDate = LocalDate.ofEpochDay(logicalEpochDay.toLong())

        assertEquals(LocalDate.parse("2023-11-02"), logicalDate)
    }

    @Test
    fun getLogicalEpochDay_atMidnightWithZeroOffset_returnsCurrentLogicalDay() {
        // 2023-11-02 00:00:00 UTC
        val instant = Instant.parse("2023-11-02T00:00:00Z")
        val zoneId = ZoneId.of("UTC")
        val offsetMinutes = 0

        val logicalEpochDay = LogicalDayCalculator.getLogicalEpochDay(instant, zoneId, offsetMinutes)
        val logicalDate = LocalDate.ofEpochDay(logicalEpochDay.toLong())

        assertEquals(LocalDate.parse("2023-11-02"), logicalDate)
    }

    @Test
    fun epochDayConversion_isBijective() {
        val originalDate = LocalDate.of(2023, 11, 15)
        val epochDay = LogicalDayCalculator.localDateToEpochDay(originalDate)
        val restoredDate = LogicalDayCalculator.epochDayToLocalDate(epochDay)

        assertEquals(originalDate, restoredDate)
    }
}
