package com.tasky.agenda.presentation.agenda

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasky.agenda.domain.repository.common.AgendaRepository
import com.tasky.agenda.presentation.common.model.FakeEventUi
import com.tasky.agenda.presentation.common.model.FakeRemainderUi
import com.tasky.agenda.presentation.common.model.FakeTaskUi
import com.tasky.core.domain.AuthInfoStorage
import kotlinx.coroutines.launch

class AgendaItemsViewModel(
    private val agendaRepository: AgendaRepository,
    private val authInfoStorage: AuthInfoStorage
) : ViewModel() {

    var state by mutableStateOf(
        AgendaItemsState(
            agendaItems = listOf(
                FakeEventUi,
                FakeRemainderUi,
                FakeTaskUi,
                FakeEventUi,
                FakeRemainderUi,
                FakeTaskUi,
                FakeEventUi,
                FakeRemainderUi,
                FakeTaskUi,
            )
        )
    )
        private set


    init {

        viewModelScope.launch {
            state = state.copy(
                fullName = authInfoStorage.get()?.fullName
            )
        }

    }


    fun onAction(action: AgendaItemsAction) {
        when (action) {
            AgendaItemsAction.OnAddEvent -> TODO()
            AgendaItemsAction.OnAddRemainder -> TODO()
            AgendaItemsAction.OnAddTask -> TODO()
            is AgendaItemsAction.OnDeleteAgendaItemUi -> TODO()
            is AgendaItemsAction.OnEditAgendaItemUi -> TODO()
            AgendaItemsAction.OnLogOut -> {
                // $todo - Needs to implement logout feature
            }

            is AgendaItemsAction.OnOpenAgendaItemUi -> TODO()
            is AgendaItemsAction.OnSelectDate -> {
                state = state.copy(
                    selectedDate = action.date
                )
            }

            is AgendaItemsAction.OnSelectSelectionStartDate -> {
                state = state.copy(
                    selectionStartDate = action.date,
                    selectedDate = action.date,
                    isDateSelectorModelOpen = false
                )
            }

            AgendaItemsAction.OnToggleAddAgendaItemDropDown -> {
                state = state.copy(
                    isAddAgendaItemDropDownOpen = !state.isAddAgendaItemDropDownOpen
                )
            }

            AgendaItemsAction.OnToggleDateSelectorModel -> {
                state = state.copy(
                    isDateSelectorModelOpen = !state.isDateSelectorModelOpen
                )
            }

            AgendaItemsAction.OnToggleLogOutDropDown -> {
                state = state.copy(
                    isLogOutDropDownOpen = !state.isLogOutDropDownOpen
                )
            }

            is AgendaItemsAction.OnSelectAgendaItemUi -> TODO()
            is AgendaItemsAction.OnToggleTaskUiCompletion -> TODO()
        }
    }


    companion object {
        const val NO_OF_DAYS_TO_RENDER = 6
    }
}