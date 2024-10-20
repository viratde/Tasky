package com.tasky.agenda.data.utils

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttendeeEntity(
    @SerialName("email") val email: String,
    @SerialName("fullName") val fullName: String,
    @SerialName("userId") val userId: String,
    @SerialName("eventId") val eventId: String,
    @SerialName("isGoing") val isGoing: Boolean,
    @SerialName("remindAt") val remindAt: Long
)