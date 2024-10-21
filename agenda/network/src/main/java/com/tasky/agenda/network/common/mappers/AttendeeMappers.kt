package com.tasky.agenda.network.common.mappers

import com.tasky.agenda.domain.model.Attendee
import com.tasky.agenda.domain.model.TemporaryNetworkAttendee
import com.tasky.agenda.network.common.dtos.AttendeeDto

fun AttendeeDto.toAttendee(): Attendee {
    return Attendee(
        email = email,
        fullName = fullName,
        userId = userId,
        eventId = eventId,
        isGoing = isGoing,
        remindAt = remindAt
    )
}

