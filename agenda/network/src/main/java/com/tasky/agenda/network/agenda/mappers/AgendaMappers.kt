package com.tasky.agenda.network.agenda.mappers

import com.tasky.agenda.domain.model.Agenda
import com.tasky.agenda.network.agenda.dtos.AgendaDto
import com.tasky.agenda.network.common.mappers.toEvent
import com.tasky.agenda.network.common.mappers.toReminder
import com.tasky.agenda.network.common.mappers.toTask

fun AgendaDto.toAgenda(): Agenda {
    return Agenda(
        events = events.map { it.toEvent() },
        tasks = tasks.map { it.toTask() },
        reminders = reminders.map { it.toReminder() }
    )
}
