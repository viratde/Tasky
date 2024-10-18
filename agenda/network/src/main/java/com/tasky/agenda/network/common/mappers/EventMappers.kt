package com.tasky.agenda.network.common.mappers

import com.tasky.agenda.domain.model.Event
import com.tasky.agenda.network.common.dtos.EventDto

fun EventDto.toEvent(): Event {
    return Event(
        id = id,
        title = title,
        description = description,
        from = from,
        to = to,
        remindAt = remindAt,
        attendees = attendees.map { it.toAttendee() },
        photos = photos.map { it.toAgendaPhoto() },
        isUserEventCreator = isUserEventCreator,
        host = host
    )
}