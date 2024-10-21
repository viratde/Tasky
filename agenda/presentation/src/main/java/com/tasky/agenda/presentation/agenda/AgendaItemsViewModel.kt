package com.tasky.agenda.presentation.agenda

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.tasky.agenda.domain.repository.common.AgendaRepository
import com.tasky.core.domain.AuthInfoStorage

class AgendaItemsViewModel(
    private val agendaRepository: AgendaRepository,
    private val authInfoStorage: AuthInfoStorage
) : ViewModel() {

    var state by mutableStateOf(AgendaItemsState())
        private set


    fun onAction(action: AgendaItemsAction) {
        when(action){
            AgendaItemsAction.OnAddEvent -> TODO()
            AgendaItemsAction.OnAddRemainder -> TODO()
            AgendaItemsAction.OnAddTask -> TODO()
            is AgendaItemsAction.OnDeleteAgendaItemUi -> TODO()
            is AgendaItemsAction.OnEditAgendaItemUi -> TODO()
            AgendaItemsAction.OnLogOut -> TODO()
            is AgendaItemsAction.OnOpenAgendaItemUi -> TODO()
            is AgendaItemsAction.OnSelectDate -> TODO()
            is AgendaItemsAction.OnSelectSelectionStartDate -> TODO()
            AgendaItemsAction.OnToggleAddAgendaItemDropDown -> TODO()
            AgendaItemsAction.OnToggleDateSelectorModel -> TODO()
            AgendaItemsAction.OnToggleLogOutDropDown -> TODO()
            is AgendaItemsAction.OnToggleSelectedAgendaItemUi -> TODO()
            is AgendaItemsAction.OnToggleTaskUiCompletion -> TODO()
        }
    }


}