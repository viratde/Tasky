package com.tasky.agenda.presentation.common.mappers

import com.tasky.agenda.domain.model.Agenda
import com.tasky.agenda.presentation.common.model.AgendaItem
import com.tasky.agenda.presentation.common.model.AgendaItemUi

fun Agenda.toAgendaItemUiList(): List<AgendaItemUi.Item> {
    return events.map { it.toAgendaItemUi() } + tasks.map { it.toAgendaItemUi() } + reminders.map { it.toAgendaItemUi() }
}

