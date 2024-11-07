package com.tasky.agenda.presentation.agenda

import com.tasky.agenda.presentation.common.model.AgendaItem
import com.tasky.agenda.presentation.common.model.AgendaItemUi

sealed interface AgendaItemsAction {

    data object OnToggleDateSelectorModel : AgendaItemsAction
    data class OnSelectDate(
        val date: Long
    ) : AgendaItemsAction

    data class OnSelectSelectionStartDate(
        val date: Long
    ) : AgendaItemsAction

    data object OnToggleLogOutDropDown : AgendaItemsAction
    data object OnLogOut : AgendaItemsAction
    data object OnToggleAddAgendaItemDropDown : AgendaItemsAction

    data object OnAddEvent : AgendaItemsAction
    data object OnAddTask : AgendaItemsAction
    data object OnAddRemainder : AgendaItemsAction

    data class OnToggleTaskUiCompletion(val taskUi: AgendaItem.TaskUi) : AgendaItemsAction

    data class OnToggleAgendaItemUi(val agendaItemId: String) : AgendaItemsAction

    data class OnOpenAgendaItemUi(val agendaItem: AgendaItemUi.Item) : AgendaItemsAction
    data class OnEditAgendaItemUi(val agendaItem: AgendaItemUi.Item) : AgendaItemsAction
    data class OnDeleteAgendaItemUi(val agendaItem: AgendaItemUi.Item) : AgendaItemsAction
}