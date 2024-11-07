package com.tasky.agenda.presentation.common.mappers

import com.tasky.agenda.domain.model.Event
import com.tasky.agenda.domain.model.Reminder
import com.tasky.agenda.domain.model.Task
import com.tasky.agenda.presentation.agenda_item_details.components.utils.RemindTimes
import com.tasky.agenda.presentation.common.model.AgendaItem
import com.tasky.agenda.presentation.common.model.AgendaItemUi

fun AgendaItem.TaskUi.toTask(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        time = time,
        isDone = isDone,
        remindAt = time - remindAt.getTimeInMilliseconds()
    )
}

fun AgendaItem.EventUi.toEvent(): Event {
    return Event(
        id = id,
        title = title,
        description = description,
        from = from,
        to = to,
        isUserEventCreator = isHost,
        remindAt = to - remindAt.getTimeInMilliseconds(),
        attendees = attendees,
        photos = photos,
        host = hostId
    )
}

fun AgendaItem.ReminderUi.toReminder(): Reminder {
    return Reminder(
        id = id,
        title = title,
        description = description,
        time = time,
        remindAt = time - remindAt.getTimeInMilliseconds()
    )
}

fun Event.toAgendaItemUi(): AgendaItemUi.Item {
    return AgendaItemUi.Item(
        item = toAgendaItem()
    )
}

fun Task.toAgendaItemUi(): AgendaItemUi.Item {
    return AgendaItemUi.Item(
        item = toAgendaItem()
    )
}

fun Reminder.toAgendaItemUi(): AgendaItemUi.Item {
    return AgendaItemUi.Item(
        item = toAgendaItem()
    )
}
