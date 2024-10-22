package com.tasky.agenda.network.common.mappers

import com.tasky.agenda.domain.model.AttendeeExistence
import com.tasky.agenda.network.common.dtos.AttendeeExistenceDto

fun AttendeeExistenceDto.AttendeeExistence.toAttendeeExistence(): AttendeeExistence {
    return AttendeeExistence(
        email = email,
        userId = userId,
        fullName = fullName
    )
}