package com.tasky.agenda.domain.model

data class Event(
    val id: String,
    val title: String,
    val description: String,
    val remindAt: Long,
    val from: Long,
    val to: Long,
    val photos: List<AgendaPhoto>,
    val attendees: List<Attendee>,
    val isUserEventCreator: Boolean,
    val host: String
)
