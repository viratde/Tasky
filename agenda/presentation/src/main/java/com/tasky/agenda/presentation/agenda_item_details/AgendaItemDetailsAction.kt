package com.tasky.agenda.presentation.event_details

import com.tasky.agenda.presentation.event_details.components.utils.RemindTimes

sealed interface EventDetailsAction {
    data class OnTitleChange(val title: String) : EventDetailsAction
    data class OnDescriptionChange(val description: String) : EventDetailsAction
    data class OnFromChange(val from: Long) : EventDetailsAction
    data class OnToChange(val to: Long) : EventDetailsAction
    data class OnAtChange(val at: Long) : EventDetailsAction
    data class OnRemindTimeChange(val remindTime: RemindTimes) : EventDetailsAction
    data object OnToggleEditMode : EventDetailsAction
}