package com.tasky.agenda.presentation.agenda_item_details

import com.tasky.agenda.presentation.agenda_item_details.components.utils.RemindTimes
import com.tasky.agenda.presentation.agenda_item_details.components.utils.VisitorsFilterState
import com.tasky.agenda.presentation.common.model.AgendaPhoto

sealed interface AgendaItemDetailsAction {
    data class OnTitleChange(val title: String) : AgendaItemDetailsAction
    data class OnDescriptionChange(val description: String) : AgendaItemDetailsAction
    data class OnFromChange(val from: Long) : AgendaItemDetailsAction
    data class OnToChange(val to: Long) : AgendaItemDetailsAction
    data class OnAtChange(val at: Long) : AgendaItemDetailsAction
    data class OnRemindTimeChange(val remindTime: RemindTimes) : AgendaItemDetailsAction
    data object OnToggleEditMode : AgendaItemDetailsAction
    data class OnAddAgendaPhoto(val photo: AgendaPhoto) : AgendaItemDetailsAction
    data object OnToggleVisitorsModel : AgendaItemDetailsAction
    data class OnVisitorsEmailChange(val email: String) : AgendaItemDetailsAction
    data object OnAddVisitor : AgendaItemDetailsAction
    data class OnVisitorFilterChange(val visitorsFilterState: VisitorsFilterState):AgendaItemDetailsAction
}