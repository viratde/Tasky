package com.tasky.agenda.network.common.mappers

import com.tasky.agenda.domain.model.Reminder
import com.tasky.agenda.network.common.dtos.ReminderDto

fun Reminder.toReminderDto(): ReminderDto {
    return ReminderDto(
        id = id,
        title = title,
        description = description,
        remindAt = remindAt,
        time = time,
    )
}

fun ReminderDto.toReminder(): Reminder {
    return Reminder(
        id = id,
        title = title,
        description = description,
        remindAt = remindAt,
        time = time,
    )
}