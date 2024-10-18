package com.tasky.agenda.network.common.mappers

import com.tasky.agenda.domain.model.Attendee
import com.tasky.agenda.network.common.dtos.AttendeeDto

fun AttendeeDto.toAttendee(): Attendee {
    return Attendee(
        email = email,
        fullName = fullName,
        userId = userId,
        isGoing = isGoing,
        remindAt = remindAt,
        eventId = eventId
    )
}

fun Attendee.toAttendeeDto(): AttendeeDto {
    return AttendeeDto(
        email = email,
        fullName = fullName,
        userId = userId,
        isGoing = isGoing,
        remindAt = remindAt,
        eventId = eventId
    )
}