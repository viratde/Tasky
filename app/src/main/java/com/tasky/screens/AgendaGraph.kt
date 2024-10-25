package com.tasky.screens

import com.tasky.agenda.presentation.agenda.AgendaItemsViewModel
import com.tasky.agenda.presentation.agenda_item_details.AgendaDetailsViewModel
import com.tasky.agenda.presentation.common.util.AgendaItemUiType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data object AgendaGraph

@Serializable
data class AgendaScreen(
    @SerialName(AgendaItemsViewModel.IS_COMING_FROM_LOGIN_SCREEN) val isComingFromLoginScreen: Boolean = false
)

@Serializable
data class AgendaItemUiScreen(
    @SerialName(AgendaDetailsViewModel.AGENDA_ITEM_UI_ID) val agendaItemId: String?,
    @SerialName(AgendaDetailsViewModel.AGENDA_ITEM_UI_TYPE) val agendaItemUiType: AgendaItemUiType,
    @SerialName(AgendaDetailsViewModel.SELECTED_DATE) val selectedDate: Long,
    @SerialName(AgendaDetailsViewModel.IS_IN_EDIT_MODE) val isInEditMode:Boolean
)

