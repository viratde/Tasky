package com.tasky.agenda.data.mappers

import com.tasky.agenda.data.utils.AttendeeEntity
import com.tasky.agenda.domain.model.Attendee

fun AttendeeEntity.toAttendee(): Attendee {
    return Attendee(
        email = email,
        userId = userId,
        fullName = fullName,
        isGoing = isGoing,
        eventId = eventId,
        remindAt = remindAt
    )
}

fun Attendee.toAttendeeEntity(): AttendeeEntity {
    return AttendeeEntity(
        email = email,
        userId = userId,
        fullName = fullName,
        isGoing = isGoing,
        eventId = eventId,
        remindAt = remindAt
    )
}