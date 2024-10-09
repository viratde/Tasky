package com.tasky.agenda.presentation.common

enum class RemindTimes(
    val value: Int,
    val unit: String,
    private val multiplier: Long
) {

    TEN_MINUTES(10, "minutes", 10L),
    THIRTY_MINUTES(30, "minutes", 30L),
    ONE_HOUR(1, "hour", 60L),
    SIX_HOURS(6, "hours", 6 * 60L),
    ONE_DAY(1, "day", 24L * 60L);

    fun getTimeInMilliseconds(): Long {
        return multiplier * 60L * 60L
    }

}