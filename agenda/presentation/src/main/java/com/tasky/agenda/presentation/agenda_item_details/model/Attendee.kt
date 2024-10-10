package com.tasky.agenda.presentation.event_details.model

data class Attendee(
    val email: String,
    val fullName: String,
    val userId: String,
    val isGoing: Boolean = true,
)

val FakeAttendee = Attendee(
    email = "Vko610@gmail.com",
    fullName = "Virat Kumar",
    userId = ""
)