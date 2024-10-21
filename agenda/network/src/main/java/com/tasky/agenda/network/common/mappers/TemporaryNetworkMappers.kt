package com.tasky.agenda.network.common.mappers

import com.tasky.agenda.domain.model.TemporaryNetworkAttendee
import com.tasky.agenda.network.common.dtos.TemporaryNetworkAttendeeDto

fun TemporaryNetworkAttendeeDto.Attendee.toTemporaryNetworkAttendee(): TemporaryNetworkAttendee {
    return TemporaryNetworkAttendee(
        email = email,
        userId = userId,
        fullName = fullName
    )
}