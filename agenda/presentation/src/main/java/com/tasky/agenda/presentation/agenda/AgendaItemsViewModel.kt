package com.tasky.agenda.presentation.agenda

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasky.agenda.domain.repository.AgendaRepository
import com.tasky.agenda.domain.repository.EventRepository
import com.tasky.agenda.domain.repository.ReminderRepository
import com.tasky.agenda.domain.repository.TaskRepository
import com.tasky.agenda.domain.schedulers.EventSyncScheduler
import com.tasky.agenda.domain.schedulers.ReminderSyncScheduler
import com.tasky.agenda.domain.schedulers.TaskSyncScheduler
import com.tasky.agenda.presentation.common.mappers.toAgendaItemUiList
import com.tasky.agenda.presentation.common.mappers.toTask
import com.tasky.agenda.presentation.common.model.AgendaItem
import com.tasky.agenda.presentation.common.model.AgendaItemUi
import com.tasky.agenda.presentation.common.util.AgendaItemType
import com.tasky.agenda.presentation.common.util.ifEventUi
import com.tasky.agenda.presentation.common.util.ifReminderUi
import com.tasky.agenda.presentation.common.util.ifTaskUi
import com.tasky.agenda.presentation.common.util.toAgendaItemUiType
import com.tasky.agenda.presentation.common.util.toComparableTime
import com.tasky.core.domain.AuthInfoStorage
import com.tasky.core.domain.util.Result
import com.tasky.core.presentation.ui.asUiText
import com.tasky.core.presentation.ui.getCurrentTimeInMillis
import kotlinx.coroutines.CoroutineScope
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
    private val savedStateHandle: SavedStateHandle,
    private val eventSyncScheduler: EventSyncScheduler,
    private val taskSyncScheduler: TaskSyncScheduler,
    private val reminderSyncScheduler: ReminderSyncScheduler,
    private val applicationScope: CoroutineScope
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
                navigateToDetailsScreen(AgendaItemType.Event)
            }

            AgendaItemsAction.OnAddRemainder -> {
                _state.update {
                    it.copy(isAddAgendaItemDropDownOpen = false)
                }
                navigateToDetailsScreen(AgendaItemType.Reminder)
            }

            AgendaItemsAction.OnAddTask -> {
                _state.update {
                    it.copy(isAddAgendaItemDropDownOpen = false)
                }
                navigateToDetailsScreen(AgendaItemType.Task)
            }

            is AgendaItemsAction.OnDeleteAgendaItemUi -> {
                deleteAgendaItem(action.agendaItem)
            }

            is AgendaItemsAction.OnEditAgendaItemUi -> {
                _state.update {
                    it.copy(selectedAgendaItemUi = null)
                }
                navigateToDetailsScreen(
                    action.agendaItem.toAgendaItemUiType(),
                    action.agendaItem.item.id,
                    true
                )
            }

            is AgendaItemsAction.OnOpenAgendaItemUi -> {
                _state.update {
                    it.copy(selectedAgendaItemUi = null)
                }
                navigateToDetailsScreen(
                    action.agendaItem.toAgendaItemUiType(),
                    action.agendaItem.item.id
                )
            }

            AgendaItemsAction.OnLogOut -> {
                logout()
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
                        selectedAgendaItemUi = if (it.selectedAgendaItemUi == action.agendaItemId) null else action.agendaItemId
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
                    val items =
                        agenda.toAgendaItemUiList().sortedBy { item -> item.toComparableTime() }
                            .let { items ->
                                val currentTime = getCurrentTimeInMillis()
                                val index =
                                    items.indexOfLast { item -> item.toComparableTime() < currentTime }
                                if (index != -1) {
                                    val mutableList = items.toMutableList<AgendaItemUi>()
                                    mutableList.add(index + 1, AgendaItemUi.Needle)
                                    return@let mutableList.toList()
                                }
                                items.toList<AgendaItemUi>()
                            }

                    it.copy(
                        agendaItems = items,
                    )
                }
            }
        }
    }

    private fun navigateToDetailsScreen(
        itemUiType: AgendaItemType,
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

    private fun deleteAgendaItem(agendaItemUi: AgendaItemUi.Item) {
        viewModelScope.launch {
            agendaItemUi
                .ifEventUi {
                    eventRepository.deleteEventById(it.id)
                }
                .ifTaskUi {
                    taskRepository.deleteTaskById(it.id)
                }
                .ifReminderUi {
                    reminderRepository.deleteRemindersById(it.id)
                }
        }
    }

    private fun toggleTaskUiAgendaItem(taskUi: AgendaItem.TaskUi) {
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

    private fun logout() {
        viewModelScope.launch {
            applicationScope.launch {
                taskSyncScheduler.cancelAllSyncs()
            }
            applicationScope.launch {
                reminderSyncScheduler.cancelAllSyncs()
            }
            applicationScope.launch {
                eventSyncScheduler.cancelAllSyncs()
            }
            applicationScope.launch {
                agendaRepository.deleteAllAgendaItems()
            }
            applicationScope.launch {
                agendaRepository.logout()
            }
            applicationScope.launch {
                authInfoStorage.set(null)
            }
        }
    }

    companion object {
        const val NO_OF_DAYS_TO_RENDER = 6
        const val IS_COMING_FROM_LOGIN_SCREEN = "isComingFromLoginScreen"
    }
}