package com.tasky.agenda.presentation.agenda_item_details

import com.tasky.core.presentation.ui.UiText

sealed interface AgendaItemDetailsEvent {
    data class OnError(val uiText: UiText) : AgendaItemDetailsEvent
}