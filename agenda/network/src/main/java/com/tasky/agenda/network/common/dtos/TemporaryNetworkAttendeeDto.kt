package com.tasky.agenda.network.common.dtos

import kotlinx.serialization.Serializable

@Serializable
data class TemporaryNetworkAttendeeDto(
    val doesUserExist: Boolean,
    val attendee: Attendee?
) {

    @Serializable
    data class Attendee(
        val email: String,
        val fullName: String,
        val userId: String,
    )

}
