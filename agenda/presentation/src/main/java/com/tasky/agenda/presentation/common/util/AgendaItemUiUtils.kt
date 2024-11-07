package com.tasky.agenda.presentation.common.util

import com.tasky.agenda.presentation.common.model.AgendaItem
import com.tasky.agenda.presentation.common.model.AgendaItemUi


fun AgendaItemUi.Item.toAgendaItemUiType(): AgendaItemType {
    return item.toAgendaItemUiType()
}

fun AgendaItemUi.Item.toComparableTime(): Long {
    return item.toComparableTime()
}

inline fun AgendaItemUi.Item.ifTaskUi(
    action: (AgendaItem.TaskUi) -> Unit
): AgendaItemUi.Item {
    if (item is AgendaItem.TaskUi) {
        action(item)
    }
    return this
}

inline fun AgendaItemUi.Item.ifEventUi(
    action: (AgendaItem.EventUi) -> Unit
): AgendaItemUi.Item {
    if (item is AgendaItem.EventUi) {
        action(item)
    }
    return this
}

inline fun AgendaItemUi.Item.ifReminderUi(
    action: (AgendaItem.ReminderUi) -> Unit
): AgendaItemUi.Item {
    if (item is AgendaItem.ReminderUi) {
        action(item)
    }
    return this
}