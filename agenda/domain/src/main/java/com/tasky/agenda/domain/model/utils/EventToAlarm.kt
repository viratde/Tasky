package com.tasky.agenda.domain.model.utils

import com.tasky.agenda.domain.model.Alarm
import com.tasky.agenda.domain.model.Event

fun Event.toAlarm(): Alarm {
    return Alarm(
        id = id,
        title = title,
        description = description,
        at = remindAt
    )
}