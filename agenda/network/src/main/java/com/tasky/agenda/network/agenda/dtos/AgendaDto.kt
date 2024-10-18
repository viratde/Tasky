package com.tasky.agenda.network.agenda.dtos

import com.tasky.agenda.network.common.dtos.EventDto
import com.tasky.agenda.network.common.dtos.ReminderDto
import com.tasky.agenda.network.common.dtos.TaskDto
import kotlinx.serialization.Serializable

@Serializable
data class AgendaDto(
    val events: List<EventDto>,
    val tasks: List<TaskDto>,
    val reminders: List<ReminderDto>
)
