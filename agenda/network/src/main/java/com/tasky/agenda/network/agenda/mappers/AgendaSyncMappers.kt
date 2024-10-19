package com.tasky.agenda.network.agenda.mappers

import com.tasky.agenda.domain.model.AgendaSync
import com.tasky.agenda.network.agenda.dtos.AgendaSyncDto

fun AgendaSync.toAgendaSyncDto(): AgendaSyncDto {
    return AgendaSyncDto(
        deletedReminderIds = deletedReminderIds,
        deletedTaskIds = deletedTaskIds,
        deletedEventIds = deletedEventIds
    )
}