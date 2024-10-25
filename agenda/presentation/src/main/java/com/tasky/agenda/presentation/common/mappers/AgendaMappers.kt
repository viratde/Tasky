package com.tasky.agenda.presentation.common.mappers

import com.tasky.agenda.domain.model.Agenda
import com.tasky.agenda.presentation.common.model.AgendaItemUi

fun Agenda.toAgendaItemUiList(): List<AgendaItemUi> {
    return events.map { it.toAgendaItemEventUi() } + tasks.map { it.toAgendaItemTaskUi() } + reminders.map { it.toAgendaItemReminderUi() }
}