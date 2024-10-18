package com.tasky.agenda.network.common.mappers

import com.tasky.agenda.domain.model.Remainder
import com.tasky.agenda.network.common.dtos.ReminderDto

fun Remainder.toReminderDto(): ReminderDto {
    return ReminderDto(
        id = id,
        title = title,
        description = description,
        remindAt = remindAt,
        time = time,
    )
}

fun ReminderDto.toReminder(): Remainder {
    return Remainder(
        id = id,
        title = title,
        description = description,
        remindAt = remindAt,
        time = time,
    )
}