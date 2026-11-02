package com.rawaq.app.domain.util

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

object LogicalDayCalculator {

    fun getLogicalEpochDay(
        instant: Instant = Instant.now(),
        zoneId: ZoneId = ZoneId.systemDefault(),
        offsetMinutes: Int = 180
    ): Int {
        val zonedDateTime = instant.atZone(zoneId)
        val adjustedDateTime = zonedDateTime.minusMinutes(offsetMinutes.toLong())
        return adjustedDateTime.toLocalDate().toEpochDay().toInt()
    }

    fun epochDayToLocalDate(epochDay: Int): LocalDate {
        return LocalDate.ofEpochDay(epochDay.toLong())
    }

    fun localDateToEpochDay(localDate: LocalDate): Int {
        return localDate.toEpochDay().toInt()
    }
}
