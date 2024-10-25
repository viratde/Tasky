package com.tasky.agenda.presentation.common.util

import com.tasky.agenda.presentation.common.model.AgendaItemUi


fun AgendaItemUi.toAgendaItemUiType():AgendaItemUiType {
    return when(this){
        is AgendaItemUi.EventUi -> AgendaItemUiType.Event
        is AgendaItemUi.ReminderUi -> AgendaItemUiType.Reminder
        is AgendaItemUi.TaskUi -> AgendaItemUiType.Task
    }
}

inline fun AgendaItemUi.ifTaskUi(
    action: (AgendaItemUi.TaskUi) -> Unit
): AgendaItemUi {
    if (this is AgendaItemUi.TaskUi) {
        action(this)
    }
    return this
}

inline fun AgendaItemUi.ifEventUi(
    action: (AgendaItemUi.EventUi) -> Unit
): AgendaItemUi {
    if (this is AgendaItemUi.EventUi) {
        action(this)
    }
    return this
}

inline fun AgendaItemUi.ifReminderUi(
    action: (AgendaItemUi.ReminderUi) -> Unit
): AgendaItemUi {
    if (this is AgendaItemUi.ReminderUi) {
        action(this)
    }
    return this
}