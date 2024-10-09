package com.tasky.core.presentation.ui

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


fun Long.toUiDate(): String {
    val zonedDateTime = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.systemDefault()
    )
    return zonedDateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))
}

fun Long.toFullUiDate(): String {
    val zonedDateTime = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.systemDefault()
    )
    return "${zonedDateTime.format(DateTimeFormatter.ofPattern("dd"))} ${zonedDateTime.month.name} ${zonedDateTime.year}"
}

fun Long.toUiTime(): String {
    val zonedDateTime = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.systemDefault()
    )
    return zonedDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
}

fun Long.getHour(): Int {
    val zonedDateTime = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.systemDefault()
    )
    return zonedDateTime.hour
}

fun Long.getMinute(): Int {
    val zonedDateTime = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.systemDefault()
    )
    return zonedDateTime.minute
}

fun Long.withHourAndMinutes(hour: Int, minutes: Int): Long {
    val zonedDateTime = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.systemDefault()
    ).withHour(hour)
        .withMinute(minutes)
    return zonedDateTime.toInstant().toEpochMilli()
}
