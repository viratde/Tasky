package com.tasky.agenda.presentation.common.mappers

import com.tasky.agenda.domain.model.Event
import com.tasky.agenda.domain.model.Reminder
import com.tasky.agenda.domain.model.Task
import com.tasky.agenda.presentation.agenda_item_details.components.utils.RemindTimes
import com.tasky.agenda.presentation.common.model.AgendaItem

fun Event.toAgendaItem(): AgendaItem {
    return AgendaItem.EventUi(
        id = id,
        title = title,
        description = description,
        from = from,
        to = to,
        remindAt = RemindTimes.getByTimeInMilliseconds(to, remindAt),
        photos = photos,
        attendees = attendees,
        isHost = isUserEventCreator,
        hostId = host
    )
}

fun Task.toAgendaItem(): AgendaItem {
    return AgendaItem.TaskUi(
        id = id,
        title = title,
        description = description,
        time = time,
        remindAt = RemindTimes.getByTimeInMilliseconds(time, remindAt),
        isDone = isDone
    )
}

fun Reminder.toAgendaItem(): AgendaItem {
    return AgendaItem.ReminderUi(
        id = id,
        title = title,
        description = description,
        time = time,
        remindAt = RemindTimes.getByTimeInMilliseconds(time, remindAt),
    )

}
