package com.tasky.agenda.network.agenda.dtos

import kotlinx.serialization.Serializable

@Serializable
data class AgendaSyncDto(
    val deletedEventIds: List<String>,
    val deletedTaskIds: List<String>,
    val deletedReminderIds: List<String>
)

