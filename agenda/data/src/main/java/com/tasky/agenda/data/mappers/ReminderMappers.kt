package com.tasky.agenda.data.mappers

import com.tasky.agenda.data.model.ReminderEntity
import com.tasky.agenda.domain.model.Reminder

fun ReminderEntity.toReminder(): Reminder {
    return Reminder(
        id = id,
        title = title,
        description = description,
        remindAt = remindAt,
        time = time
    )
}

fun Reminder.toReminderEntity(): ReminderEntity {
    return ReminderEntity(
        id = id,
        title = title,
        description = description,
        remindAt = remindAt,
        time = time
    )
}