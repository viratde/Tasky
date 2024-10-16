package com.tasky.agenda.presentation.agenda

import com.tasky.agenda.presentation.common.model.AgendaItemUi
import java.time.LocalDate

sealed interface AgendaItemsAction {

    data object OnToggleDateSelectorModel : AgendaItemsAction
    data class OnSelectDate(
        val date: LocalDate
    ) : AgendaItemsAction

    data class OnSelectSelectionStartDate(
        val date: LocalDate
    ) : AgendaItemsAction

    data object OnToggleLogOutDropDown : AgendaItemsAction
    data object OnLogOut : AgendaItemsAction
    data object OnToggleAddAgendaItemDropDown : AgendaItemsAction

    // I am not sure of i want to keep these actions independent or not, i will decide it later but for now i am keeping it independent
    data object OnAddEvent : AgendaItemsAction
    data object OnAddTask : AgendaItemsAction
    data object OnAddRemainder : AgendaItemsAction

    data class OnToggleTaskUiCompletion(val taskUi: AgendaItemUi.TaskUi) : AgendaItemsAction

    data class OnToggleSelectedAgendaItemUi(val agendaItemUi: AgendaItemUi?) : AgendaItemsAction

    data class OnOpenAgendaItemUi(val agendaItemUi: AgendaItemUi) : AgendaItemsAction
    data class OnEditAgendaItemUi(val agendaItemUi: AgendaItemUi) : AgendaItemsAction
    data class OnDeleteAgendaItemUi(val agendaItemUi: AgendaItemUi) : AgendaItemsAction
}