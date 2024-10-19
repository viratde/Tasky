package com.tasky.core.data.utils

import java.time.Instant
import java.time.temporal.ChronoUnit


fun Long.getStartOfDay(): Long {
    return Instant
        .ofEpochMilli(this)
        .truncatedTo(ChronoUnit.DAYS)
        .toEpochMilli()
}

fun Long.getEndOfDay(): Long {
    return Instant
        .ofEpochMilli(this)
        .truncatedTo(ChronoUnit.DAYS)
        .plus(1, ChronoUnit.DAYS)
        .toEpochMilli()
}