package com.tasky.agenda.data.utils

import kotlinx.serialization.Serializable

@Serializable
data class AttendeeEntity(
    val email: String,
    val fullName: String,
    val userId: String,
    val eventId: String,
    val isGoing: Boolean,
    val remindAt: Long
)