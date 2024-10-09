package com.tasky.agenda.presentation.event_details

import com.tasky.agenda.presentation.event_details.model.EventUi

data class EventDetailsState(
    val eventUi: EventUi? = null,
    val isLoading: Boolean = false,
    val isInEditMode: Boolean = false
)