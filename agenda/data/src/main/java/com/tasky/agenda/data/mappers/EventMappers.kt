package com.tasky.agenda.data.mappers

import com.tasky.agenda.data.model.EventEntity
import com.tasky.agenda.domain.model.AgendaPhoto
import com.tasky.agenda.domain.model.Event

fun EventEntity.toEvent(): Event {
    return Event(
        id = id,
        title = title,
        description = description,
        from = from,
        to = to,
        remindAt = remindAt,
        isUserEventCreator = isUserEventCreator,
        host = host,
        photos = photos.map { it.toPhoto() },
        attendees = attendees.map { it.toAttendee() }
    )
}

fun Event.toEventEntity(): EventEntity {
    return EventEntity(
        id = id,
        title = title,
        description = description,
        from = from,
        to = to,
        remindAt = remindAt,
        isUserEventCreator = isUserEventCreator,
        host = host,
        photos = photos.filterIsInstance<AgendaPhoto.RemotePhoto>().map { it.toPhotoEntity() },
        attendees = attendees.map { it.toAttendeeEntity() }
    )
}