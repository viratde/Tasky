package com.tasky.agenda.presentation.agenda_item_details

import com.tasky.agenda.presentation.agenda_item_details.components.utils.RemindTimes

sealed interface AgendaItemDetailsAction {
    data class OnTitleChange(val title: String) : AgendaItemDetailsAction
    data class OnDescriptionChange(val description: String) : AgendaItemDetailsAction
    data class OnFromChange(val from: Long) : AgendaItemDetailsAction
    data class OnToChange(val to: Long) : AgendaItemDetailsAction
    data class OnAtChange(val at: Long) : AgendaItemDetailsAction
    data class OnRemindTimeChange(val remindTime: RemindTimes) : AgendaItemDetailsAction
    data object OnToggleEditMode : AgendaItemDetailsAction
}