package com.tasky.agenda.presentation.agenda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasky.agenda.domain.repository.common.AgendaRepository
import com.tasky.core.domain.AuthInfoStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AgendaItemsViewModel(
    private val agendaRepository: AgendaRepository,
    private val authInfoStorage: AuthInfoStorage
) : ViewModel() {

    private val _state = MutableStateFlow(AgendaItemsState())
    val state = _state
        .onStart {
            loadFullName()
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            AgendaItemsState()
        )


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
                _state.update {
                    it.copy(
                        selectedDate = action.date
                    )
                }
            }

            is AgendaItemsAction.OnSelectSelectionStartDate -> {
                _state.update {
                    it.copy(
                        selectionStartDate = action.date,
                        selectedDate = action.date,
                        isDateSelectorModelOpen = false
                    )
                }
            }

            AgendaItemsAction.OnToggleAddAgendaItemDropDown -> {
                _state.update {
                    it.copy(
                        isAddAgendaItemDropDownOpen = !it.isAddAgendaItemDropDownOpen
                    )
                }
            }

            AgendaItemsAction.OnToggleDateSelectorModel -> {
                _state.update {
                    it.copy(
                        isDateSelectorModelOpen = !it.isDateSelectorModelOpen
                    )
                }
            }

            AgendaItemsAction.OnToggleLogOutDropDown -> {
                _state.update {
                    it.copy(
                        isLogOutDropDownOpen = !it.isLogOutDropDownOpen
                    )
                }
            }

            is AgendaItemsAction.OnSelectAgendaItemUi -> TODO()
            is AgendaItemsAction.OnToggleTaskUiCompletion -> TODO()
        }
    }


    private fun loadFullName() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    fullName = authInfoStorage.get()?.fullName
                )
            }
        }
    }

    companion object {
        const val NO_OF_DAYS_TO_RENDER = 6
    }
}