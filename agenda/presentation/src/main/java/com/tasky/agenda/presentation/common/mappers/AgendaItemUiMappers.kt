package com.tasky.agenda.presentation.common.mappers

import com.tasky.agenda.domain.model.Event
import com.tasky.agenda.domain.model.Reminder
import com.tasky.agenda.domain.model.Task
import com.tasky.agenda.presentation.agenda_item_details.components.utils.RemindTimes
import com.tasky.agenda.presentation.common.model.AgendaItemUi

fun AgendaItemUi.TaskUi.toTask(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        time = time,
        isDone = isDone,
        remindAt = time - remindAt.getTimeInMilliseconds()
    )
}

fun AgendaItemUi.EventUi.toEvent(
    host: String
): Event {
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
        host = host
    )
}

fun AgendaItemUi.ReminderUi.toReminder(
    host: String
): Reminder {
    return Reminder(
        id = id,
        title = title,
        description = description,
        time = time,
        remindAt = time - remindAt.getTimeInMilliseconds()
    )
}

fun Event.toAgendaItemEventUi(): AgendaItemUi.EventUi {
    return AgendaItemUi.EventUi(
        id = id,
        title = title,
        description = description,
        from = from,
        to = to,
        remindAt = RemindTimes.getByTimeInMilliseconds(to, remindAt),
        photos = photos,
        attendees = attendees,
        isHost = isUserEventCreator
    )
}

fun Task.toAgendaItemTaskUi(): AgendaItemUi.TaskUi {
    return AgendaItemUi.TaskUi(
        id = id,
        title = title,
        description = description,
        time = time,
        remindAt = RemindTimes.getByTimeInMilliseconds(time, remindAt),
        isDone = isDone
    )
}

fun Reminder.toAgendaItemReminderUi(): AgendaItemUi.ReminderUi {
    return AgendaItemUi.ReminderUi(
        id = id,
        title = title,
        description = description,
        time = time,
        remindAt = RemindTimes.getByTimeInMilliseconds(time, remindAt),
    )
}
