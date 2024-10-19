package com.tasky.agenda.data.utils

data class AttendeeEntity(
    val email: String,
    val fullName: String,
    val userId: String,
    val eventId: String,
    val isGoing: Boolean,
    val remindAt: Long
)