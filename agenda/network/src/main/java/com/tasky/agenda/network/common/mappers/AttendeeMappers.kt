package com.tasky.agenda.network.common.mappers

import com.tasky.agenda.domain.model.TemporaryNetworkAttendee
import com.tasky.agenda.network.common.dtos.AttendeeDto

fun AttendeeDto.Attendee.toTemporaryNetworkAttendee(): TemporaryNetworkAttendee {
    return TemporaryNetworkAttendee(
        email = email,
        fullName = fullName,
        userId = userId,
    )
}