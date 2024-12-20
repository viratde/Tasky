package com.tasky.agenda.presentation.agenda_item_details.components.utils

import com.tasky.agenda.presentation.R
import com.tasky.core.presentation.ui.UiText
import kotlin.math.abs

enum class RemindTimes(
    private val multiplier: Long
) {

    TEN_MINUTES(10L),
    THIRTY_MINUTES(30L),
    ONE_HOUR(60L),
    SIX_HOURS(6 * 60L),
    ONE_DAY(24L * 60L);

    fun getTimeInMilliseconds(): Long {
        return multiplier * 60L * 1000L
    }

    companion object {
        fun getByTimeInMilliseconds(time: Long, remindTimeInMilliSeconds: Long): RemindTimes {
            val difference = time - remindTimeInMilliSeconds
            return RemindTimes.entries.minBy {
                abs(it.getTimeInMilliseconds() - difference)
            }
        }
    }
}


fun RemindTimes.asUiText(): UiText {
    return when (this) {
        RemindTimes.TEN_MINUTES -> UiText.StringResource(R.string.ten_minutes_before)
        RemindTimes.THIRTY_MINUTES -> UiText.StringResource(R.string.thirty_minutes_before)
        RemindTimes.ONE_HOUR -> UiText.StringResource(R.string.one_hour_before)
        RemindTimes.SIX_HOURS -> UiText.StringResource(R.string.six_hours_before)
        RemindTimes.ONE_DAY -> UiText.StringResource(R.string.one_day_before)
    }
}