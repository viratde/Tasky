package com.tasky.agenda.network.event.mappers

import com.tasky.agenda.domain.model.Event
import com.tasky.agenda.network.event.dtos.CreateEventDto
import com.tasky.agenda.network.event.dtos.UpdateEventDto

fun Event.toCreateEventDto(): CreateEventDto {
    return CreateEventDto(
        id = id,
        title = title,
        description = description,
        from = from,
        to = to,
        remindAt = remindAt,
        attendeeIds = attendees.map { it.userId }
    )
}

fun Event.toUpdateEventDto(
    deletedPhotoKeys: List<String> = listOf()
): UpdateEventDto {
    return UpdateEventDto(
        id = id,
        title = title,
        description = description,
        from = from,
        to = to,
        remindAt = remindAt,
        attendeeIds = attendees.map { it.userId },
        deletedPhotoKeys = deletedPhotoKeys
    )
}