package com.tasky.agenda.data.mappers

import com.tasky.agenda.data.model.ReminderEntity
import com.tasky.agenda.domain.model.Remainder

fun ReminderEntity.toReminder(): Remainder {
    return Remainder(
        id = id,
        title = title,
        description = description,
        remindAt = remindAt,
        time = time
    )
}

fun Remainder.toReminderEntity(): ReminderEntity {
    return ReminderEntity(
        id = id,
        title = title,
        description = description,
        remindAt = remindAt,
        time = time
    )
}