package com.tasky.agenda.domain.model

data class AgendaSync(
    val deletedEventIds: List<String>,
    val deletedTaskIds: List<String>,
    val deletedReminderIds: List<String>
)
