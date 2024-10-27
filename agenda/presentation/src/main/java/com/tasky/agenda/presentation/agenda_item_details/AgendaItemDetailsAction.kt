package com.tasky.agenda.presentation.agenda_item_details

import com.tasky.agenda.presentation.agenda_item_details.components.utils.RemindTimes
import com.tasky.agenda.presentation.agenda_item_details.components.utils.VisitorsFilterState
import com.tasky.agenda.domain.model.AgendaPhoto
import com.tasky.agenda.domain.model.Attendee
import com.tasky.agenda.presentation.common.model.AgendaItemUi

sealed interface AgendaItemDetailsAction {
    data class OnTitleChange(val title: String) : AgendaItemDetailsAction
    data class OnDescriptionChange(val description: String) : AgendaItemDetailsAction
    data class OnFromChange(val from: Long) : AgendaItemDetailsAction
    data class OnToChange(val to: Long) : AgendaItemDetailsAction
    data class OnAtChange(val at: Long) : AgendaItemDetailsAction
    data class OnRemindTimeChange(val remindTime: RemindTimes) : AgendaItemDetailsAction
    data object OnToggleEditMode : AgendaItemDetailsAction
    data class OnAddAgendaPhoto(
        val photo: AgendaPhoto.LocalPhoto,
        val mimeType: String?
    ) : AgendaItemDetailsAction

    data object OnToggleVisitorsModel : AgendaItemDetailsAction
    data class OnVisitorsEmailChange(val email: String) : AgendaItemDetailsAction
    data class OnAddVisitor(val email: String) : AgendaItemDetailsAction
    data class OnVisitorFilterChange(val visitorsFilterState: VisitorsFilterState) :
        AgendaItemDetailsAction

    data class OnDeleteVisitor(val attendee: Attendee) : AgendaItemDetailsAction
    data class OnDeleteAgendaPhoto(val agendaPhoto: AgendaPhoto) : AgendaItemDetailsAction

    data object OnSaveAgendaItem : AgendaItemDetailsAction

    data class OnDeleteAgendaItem(
        val agendaItemUi: AgendaItemUi
    ) : AgendaItemDetailsAction

    data class OnLeaveAgendaItemEventIi(
        val eventUi: AgendaItemUi.EventUi
    ) : AgendaItemDetailsAction
}