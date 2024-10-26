package com.tasky.agenda.presentation.common.mappers

import com.tasky.agenda.domain.model.Attendee
import com.tasky.agenda.domain.model.AttendeeExistence

fun AttendeeExistence.toAttendee(
    remindAt: Long,
    eventId: String
): Attendee {
    return Attendee(
        fullName = fullName,
        userId = userId,
        email = email,
        isGoing = true,
        remindAt = remindAt,
        eventId = eventId,
    )
}