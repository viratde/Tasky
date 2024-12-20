package com.tasky.agenda.presentation.agenda

import com.tasky.agenda.presentation.common.util.AgendaItemType
import com.tasky.core.presentation.ui.UiText

sealed interface AgendaItemsEvent {
    data class OnError(val error: UiText) : AgendaItemsEvent
    data class OnNavigate(
        val itemUiType: AgendaItemType,
        val selectedDate: Long,
        val agendaItemUiId: String?,
        val isInEditMode:Boolean
    ) : AgendaItemsEvent
}