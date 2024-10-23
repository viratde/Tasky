package com.tasky.agenda.presentation.agenda_item_details.mappers

import com.tasky.agenda.domain.model.Task
import com.tasky.agenda.presentation.common.model.AgendaItemUi

fun AgendaItemUi.TaskUi.toTask(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        time = time,
        isDone = isDone,
        remindAt = time + remindAt.getTimeInMilliseconds()
    )
}