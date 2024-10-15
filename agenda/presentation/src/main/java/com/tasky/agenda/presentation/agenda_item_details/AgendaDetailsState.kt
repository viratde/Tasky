package com.tasky.agenda.presentation.agenda_item_details

import com.tasky.agenda.presentation.agenda_item_details.components.utils.VisitorsFilterState
import com.tasky.agenda.presentation.common.model.AgendaItemUi
import com.tasky.agenda.presentation.agenda_item_details.model.VisitorState


data class AgendaDetailsState(
    val agendaItemUi: AgendaItemUi? = null,
    val isLoading: Boolean = false,
    val isInEditMode: Boolean = false,
    val selectedVisitorsFilterState: VisitorsFilterState = VisitorsFilterState.ALL,
    val visitorState: VisitorState? = null
)