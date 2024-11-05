package com.tasky.agenda.data.mappers

import com.tasky.agenda.domain.model.Alarm
import com.tasky.agenda.domain.model.Event
import com.tasky.agenda.domain.model.Reminder
import com.tasky.agenda.domain.model.Task

fun Event.toAlarm(): Alarm {
    return Alarm(
        id = id,
        title = title,
        description = description,
        at = remindAt
    )
}

fun Reminder.toAlarm(): Alarm {
    return Alarm(
        id = id,
        title = title,
        description = description,
        at = remindAt
    )
}

fun Task.toAlarm(): Alarm {
    return Alarm(
        id = id,
        title = title,
        description = description,
        at = remindAt
    )
}