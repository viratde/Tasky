package com.tasky.screens

import com.tasky.agenda.presentation.agenda_item_details.AgendaDetailsViewModel
import com.tasky.agenda.presentation.common.util.AgendaItemUiType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data object AgendaGraph

@Serializable
data object AgendaScreen

@Serializable
data class AgendaItemUiScreen(
    @SerialName(AgendaDetailsViewModel.AGENDA_ITEM_UI_ID) val agendaItemId: String?,
    @SerialName(AgendaDetailsViewModel.AGENDA_ITEM_UI_TYPE) val agendaItemUiType: AgendaItemUiType,
    @SerialName(AgendaDetailsViewModel.SELECTED_DATE) val selectedDate: Long
)

