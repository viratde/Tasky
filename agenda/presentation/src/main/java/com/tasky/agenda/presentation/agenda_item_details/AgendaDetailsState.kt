package com.tasky.agenda.presentation.event_details

import com.tasky.agenda.presentation.event_details.components.utils.VisitorsFilterState
import com.tasky.agenda.presentation.event_details.model.AgendaItemUi


data class EventDetailsState(
    val agendaItemUi: AgendaItemUi? = null,
    val isLoading: Boolean = false,
    val isInEditMode: Boolean = false,
    val selectedVisitorsFilterState: VisitorsFilterState = VisitorsFilterState.ALL,

)