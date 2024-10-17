package com.tasky.agenda.domain.model

import java.time.ZonedDateTime

data class Attendee(
    val email: String,
    val fullName: String,
    val userId: String,
    val eventId: String,
    val isGoing: Boolean = true,
    val remindAt: Long
)

val FakeAttendee = Attendee(
    email = "Vko610@gmail.com",
    fullName = "Virat Kumar",
    userId = "",
    remindAt = ZonedDateTime.now().toInstant().toEpochMilli(),
    eventId = ""
)