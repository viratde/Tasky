package com.tasky.agenda.presentation.agenda

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasky.agenda.domain.repository.AgendaRepository
import com.tasky.agenda.domain.repository.EventRepository
import com.tasky.agenda.domain.repository.ReminderRepository
import com.tasky.agenda.domain.repository.TaskRepository
import com.tasky.agenda.presentation.common.mappers.toAgendaItemUiList
import com.tasky.agenda.presentation.common.mappers.toTask
import com.tasky.agenda.presentation.common.model.AgendaItemUi
import com.tasky.agenda.presentation.common.util.AgendaItemUiType
import com.tasky.agenda.presentation.common.util.ifEventUi
import com.tasky.agenda.presentation.common.util.ifReminderUi
import com.tasky.agenda.presentation.common.util.ifTaskUi
import com.tasky.agenda.presentation.common.util.toAgendaItemUiType
import com.tasky.core.domain.AuthInfoStorage
import com.tasky.core.domain.util.Result
import com.tasky.core.presentation.ui.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AgendaItemsViewModel(
    private val agendaRepository: AgendaRepository,
    private val authInfoStorage: AuthInfoStorage,
    private val eventRepository: EventRepository,
    private val taskRepository: TaskRepository,
    private val reminderRepository: ReminderRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(AgendaItemsState())
    val state = _state
        .onStart {
            loadFullName()
            refreshRemote()
            loadAgendaItems(_state.value.selectedDate)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            AgendaItemsState()
        )

    private val _events = Channel<AgendaItemsEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: AgendaItemsAction) {
        when (action) {
            AgendaItemsAction.OnAddEvent -> {
                _state.update {
                    it.copy(isAddAgendaItemDropDownOpen = false)
                }
                navigateToDetailsScreen(AgendaItemUiType.Event)
            }

            AgendaItemsAction.OnAddRemainder -> {
                _state.update {
                    it.copy(isAddAgendaItemDropDownOpen = false)
                }
                navigateToDetailsScreen(AgendaItemUiType.Reminder)
            }

            AgendaItemsAction.OnAddTask -> {
                _state.update {
                    it.copy(isAddAgendaItemDropDownOpen = false)
                }
                navigateToDetailsScreen(AgendaItemUiType.Task)
            }

            is AgendaItemsAction.OnDeleteAgendaItemUi -> {
                deleteAgendaItem(action.agendaItemUi)
            }

            is AgendaItemsAction.OnEditAgendaItemUi -> {
                _state.update {
                    it.copy(selectedAgendaItemUi = null)
                }
                navigateToDetailsScreen(
                    action.agendaItemUi.toAgendaItemUiType(),
                    action.agendaItemUi.id,
                    true
                )
            }

            is AgendaItemsAction.OnOpenAgendaItemUi -> {
                _state.update {
                    it.copy(selectedAgendaItemUi = null)
                }
                navigateToDetailsScreen(
                    action.agendaItemUi.toAgendaItemUiType(),
                    action.agendaItemUi.id
                )
            }

            AgendaItemsAction.OnLogOut -> {
                // $todo - Needs to implement logout feature
            }

            is AgendaItemsAction.OnSelectDate -> {
                _state.update {
                    it.copy(
                        selectedDate = action.date
                    )
                }
                loadAgendaItems(time = action.date)
            }

            is AgendaItemsAction.OnSelectSelectionStartDate -> {
                _state.update {
                    it.copy(
                        selectionStartDate = action.date,
                        selectedDate = action.date,
                        isDateSelectorModelOpen = false
                    )
                }
                loadAgendaItems(time = action.date)
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

            is AgendaItemsAction.OnToggleAgendaItemUi -> {
                _state.update {
                    it.copy(
                        selectedAgendaItemUi = if (it.selectedAgendaItemUi == action.agendaItemUiId) null else action.agendaItemUiId
                    )
                }
            }

            is AgendaItemsAction.OnToggleTaskUiCompletion -> {
                toggleTaskUiAgendaItem(action.taskUi)
            }
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

    private fun refreshRemote() {
        viewModelScope.launch {
            val isComingFromLoginScreen = savedStateHandle[IS_COMING_FROM_LOGIN_SCREEN] ?: false
            if (isComingFromLoginScreen) {
                agendaRepository.fetchAgendaItems()
            } else {
                agendaRepository.fetchAgendaItemsByTime(state.value.selectedDate)
            }
        }
    }

    private fun loadAgendaItems(time: Long) {
        viewModelScope.launch {
            agendaRepository.getAgendaItemsByTime(time).collect { agenda ->
                _state.update {
                    it.copy(
                        agendaItems = agenda.toAgendaItemUiList()
                    )
                }
            }
        }
    }

    private fun navigateToDetailsScreen(
        itemUiType: AgendaItemUiType,
        agendaItemUiId: String? = null,
        isInEditMode: Boolean = false
    ) {
        viewModelScope.launch {
            delay(25) // Added Artificial Delay Due to Dropdown issue
            _events.send(
                AgendaItemsEvent.OnNavigate(
                    itemUiType = itemUiType,
                    selectedDate = state.value.selectedDate,
                    agendaItemUiId = agendaItemUiId,
                    isInEditMode = isInEditMode
                )
            )
        }
    }

    private fun deleteAgendaItem(agendaItemUi: AgendaItemUi) {
        viewModelScope.launch {
            agendaItemUi
                .ifEventUi {
                    eventRepository.deleteEventById(it.id)
                }
                .ifTaskUi {
                    reminderRepository.deleteRemindersById(it.id)
                }
                .ifReminderUi {
                    taskRepository.deleteTaskById(it.id)
                }
        }
    }

    private fun toggleTaskUiAgendaItem(taskUi: AgendaItemUi.TaskUi) {
        viewModelScope.launch {
            val result = taskRepository.updateTask(taskUi.copy(isDone = !taskUi.isDone).toTask())
            when (result) {
                is Result.Error -> {
                    _events.send(AgendaItemsEvent.OnError(result.error.asUiText()))
                }

                is Result.Success -> Unit
            }
        }
    }

    companion object {
        const val NO_OF_DAYS_TO_RENDER = 6
        const val IS_COMING_FROM_LOGIN_SCREEN = "isComingFromLoginScreen"
    }
}