package com.tasky.agenda.network.common.dtos

import kotlinx.serialization.Serializable

@Serializable
data class AttendeeExistenceDto(
    val doesUserExist: Boolean,
    val attendeeExistence: AttendeeExistence?
) {

    @Serializable
    data class AttendeeExistence(
        val email: String,
        val fullName: String,
        val userId: String,
    )

}
