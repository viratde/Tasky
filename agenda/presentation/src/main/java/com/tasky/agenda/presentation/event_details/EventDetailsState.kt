package com.tasky.agenda.presentation.event_details

import com.tasky.agenda.presentation.event_details.model.AgendaItemUi


data class EventDetailsState(
    val eventUi: AgendaItemUi? = null,
    val isLoading: Boolean = false,
    val isInEditMode: Boolean = false
)