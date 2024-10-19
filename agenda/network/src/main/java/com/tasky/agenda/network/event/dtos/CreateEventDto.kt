package com.tasky.agenda.network.event.dtos

import kotlinx.serialization.Serializable

@Serializable
data class CreateEventDto(
    val id: String,
    val title: String,
    val description: String,
    val from: Long,
    val to: Long,
    val remindAt: Long,
    val attendeeIds: List<String>
)
