package com.tasky.agenda.presentation.common.util

import com.tasky.agenda.presentation.common.model.AgendaItem

fun AgendaItem.toAgendaItemUiType(): AgendaItemType {
    return when (this) {
        is AgendaItem.EventUi -> AgendaItemType.Event
        is AgendaItem.ReminderUi -> AgendaItemType.Reminder
        is AgendaItem.TaskUi -> AgendaItemType.Task
    }
}

fun AgendaItem.toComparableTime(): Long {
    return when (this) {
        is AgendaItem.EventUi -> from
        is AgendaItem.ReminderUi -> time
        is AgendaItem.TaskUi -> time
    }
}

inline fun AgendaItem.ifTaskUi(
    action: (AgendaItem.TaskUi) -> Unit
): AgendaItem {
    if (this is AgendaItem.TaskUi) {
        action(this)
    }
    return this
}

inline fun AgendaItem.ifEventUi(
    action: (AgendaItem.EventUi) -> Unit
): AgendaItem {
    if (this is AgendaItem.EventUi) {
        action(this)
    }
    return this
}

inline fun AgendaItem.ifReminderUi(
    action: (AgendaItem.ReminderUi) -> Unit
): AgendaItem {
    if (this is AgendaItem.ReminderUi) {
        action(this)
    }
    return this
}