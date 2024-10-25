package com.tasky.agenda.presentation.agenda_item_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasky.agenda.domain.repository.EventRepository
import com.tasky.agenda.domain.repository.ReminderRepository
import com.tasky.agenda.domain.repository.TaskRepository
import com.tasky.agenda.presentation.agenda_item_details.components.utils.RemindTimes
import com.tasky.agenda.presentation.common.mappers.toTask
import com.tasky.agenda.presentation.agenda_item_details.model.VisitorState
import com.tasky.agenda.presentation.common.mappers.toAgendaItemEventUi
import com.tasky.agenda.presentation.common.mappers.toAgendaItemReminderUi
import com.tasky.agenda.presentation.common.mappers.toAgendaItemTaskUi
import com.tasky.agenda.presentation.common.model.AgendaItemUi
import com.tasky.agenda.presentation.common.util.AgendaItemUiType
import com.tasky.core.domain.util.onError
import com.tasky.core.domain.util.onSuccess
import com.tasky.core.presentation.ui.asUiText
import com.tasky.core.presentation.ui.withCurrentTimeHourAndMinutes
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID

class AgendaDetailsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val taskRepository: TaskRepository,
    private val eventRepository: EventRepository,
    private val reminderRepository: ReminderRepository
) : ViewModel() {


    private val _state = MutableStateFlow(AgendaDetailsState())
    val state = _state
        .onStart {
            loadAgendaItem()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            AgendaDetailsState()
        )

    private val _events = Channel<AgendaItemDetailsEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: AgendaItemDetailsAction) {
        when (action) {
            is AgendaItemDetailsAction.OnAtChange -> {
                when (val item = _state.value.agendaItemUi) {
                    is AgendaItemUi.ReminderUi -> {
                        println("The change in value of at is${action.at}")
                        _state.update {
                            it.copy(
                                agendaItemUi = item.copy(
                                    time = action.at
                                )
                            )
                        }
                    }

                    is AgendaItemUi.TaskUi -> {
                        println("The change in value of at is${action.at}")
                        _state.update {
                            it.copy(
                                agendaItemUi = item.copy(
                                    time = action.at
                                )
                            )
                        }
                    }

                    else -> Unit
                }
            }

            is AgendaItemDetailsAction.OnDescriptionChange -> {
                _state.update {
                    it.copy(
                        agendaItemUi = it.agendaItemUi?.copy(
                            description = action.description
                        )
                    )
                }
            }

            is AgendaItemDetailsAction.OnFromChange -> {
                when (val item = state.value.agendaItemUi) {
                    is AgendaItemUi.EventUi -> {
                        _state.update {
                            it.copy(
                                agendaItemUi = item.copy(
                                    from = action.from
                                )
                            )
                        }
                    }

                    else -> Unit
                }
            }

            is AgendaItemDetailsAction.OnRemindTimeChange -> {
                _state.update {
                    it.copy(
                        agendaItemUi = it.agendaItemUi?.copy(
                            remindAt = action.remindTime
                        )
                    )
                }
            }

            is AgendaItemDetailsAction.OnTitleChange -> {
                _state.update {
                    it.copy(
                        agendaItemUi = it.agendaItemUi?.copy(
                            title = action.title
                        )
                    )
                }
            }

            is AgendaItemDetailsAction.OnToChange -> {
                when (val item = state.value.agendaItemUi) {
                    is AgendaItemUi.EventUi -> {
                        _state.update {
                            it.copy(
                                agendaItemUi = item.copy(
                                    to = action.to
                                )
                            )
                        }
                    }

                    else -> Unit
                }
            }

            AgendaItemDetailsAction.OnToggleEditMode -> {
                _state.update {
                    it.copy(
                        isInEditMode = !it.isInEditMode
                    )
                }
            }

            is AgendaItemDetailsAction.OnAddAgendaPhoto -> {
                if (state.value.agendaItemUi is AgendaItemUi.EventUi) {
                    _state.update {
                        it.copy(
                            agendaItemUi = updateDetailsIfEvent { item ->
                                item.copy(
                                    photos = item.photos + action.photo
                                )
                            }
                        )
                    }
                }
            }

            AgendaItemDetailsAction.OnAddVisitor -> {

            }

            AgendaItemDetailsAction.OnToggleVisitorsModel -> {
                _state.update {
                    it.copy(
                        visitorState = if (it.visitorState == null) VisitorState() else null
                    )
                }
            }

            is AgendaItemDetailsAction.OnVisitorsEmailChange -> {
                _state.update {
                    it.copy(
                        visitorState = it.visitorState?.copy(
                            email = action.email,
                            isValidEmail = true
                        )
                    )
                }
            }

            is AgendaItemDetailsAction.OnVisitorFilterChange -> {
                _state.update {
                    it.copy(
                        selectedVisitorsFilterState = action.visitorsFilterState
                    )
                }
            }

            AgendaItemDetailsAction.OnSaveAgendaItem -> {
                saveAgendaItems()
            }
        }
    }

    private fun updateDetailsIfEvent(
        update: (AgendaItemUi.EventUi) -> AgendaItemUi.EventUi
    ): AgendaItemUi? {
        return when (val details = state.value.agendaItemUi) {
            is AgendaItemUi.EventUi -> update(details)
            else -> details
        }
    }

    private fun loadAgendaItem() {

        val agendaItemUiType = savedStateHandle.get<AgendaItemUiType>(AGENDA_ITEM_UI_TYPE)
        val agendaItemUiId = savedStateHandle.get<String>(AGENDA_ITEM_UI_ID)
        val selectedDate =
            savedStateHandle.get<Long?>(SELECTED_DATE)?.withCurrentTimeHourAndMinutes()
                ?: Instant.now().toEpochMilli()
        val isInEditMode = savedStateHandle[IS_IN_EDIT_MODE] ?: false

        if (agendaItemUiId == null) {
            when (agendaItemUiType) {
                AgendaItemUiType.Reminder -> {
                    _state.update {
                        it.copy(
                            agendaItemUi = AgendaItemUi.ReminderUi(
                                id = UUID.randomUUID().toString(),
                                title = "New Reminder",
                                description = "Reminder Description",
                                remindAt = RemindTimes.TEN_MINUTES,
                                time = selectedDate
                            ),
                            isInEditMode = true
                        )
                    }
                }

                AgendaItemUiType.Event -> {
                    _state.update {
                        it.copy(
                            agendaItemUi = AgendaItemUi.EventUi(
                                id = UUID.randomUUID().toString(),
                                title = "New Event",
                                description = "Event Description",
                                remindAt = RemindTimes.TEN_MINUTES,
                                from = selectedDate,
                                to = Instant.ofEpochMilli(selectedDate).plus(10, ChronoUnit.MINUTES).toEpochMilli(),
                                attendees = listOf(),
                                photos = listOf(),
                                isHost = true
                            ),
                            isInEditMode = true
                        )
                    }
                }

                AgendaItemUiType.Task -> {
                    _state.update {
                        it.copy(
                            agendaItemUi = AgendaItemUi.TaskUi(
                                id = UUID.randomUUID().toString(),
                                title = "New Task",
                                description = "Task Description",
                                remindAt = RemindTimes.TEN_MINUTES,
                                time = selectedDate,
                                isDone = false
                            ),
                            isInEditMode = true
                        )
                    }
                }

                null -> Unit
            }

        } else {
            viewModelScope.launch {
                when (agendaItemUiType) {
                    AgendaItemUiType.Reminder -> {
                        val agendaItem = reminderRepository.getReminderById(agendaItemUiId)
                            ?.toAgendaItemReminderUi() ?: return@launch
                        _state.update {
                            it.copy(
                                editingAgendaItemUi = agendaItem,
                                agendaItemUi = agendaItem,
                                isInEditMode = isInEditMode
                            )
                        }
                    }

                    AgendaItemUiType.Event -> {
                        val agendaItem =
                            eventRepository.getEventById(agendaItemUiId)?.toAgendaItemEventUi()
                                ?: return@launch
                        _state.update {
                            it.copy(
                                editingAgendaItemUi = agendaItem,
                                agendaItemUi = agendaItem,
                                isInEditMode = isInEditMode
                            )
                        }
                    }

                    AgendaItemUiType.Task -> {
                        val agendaItem =
                            taskRepository.getTaskById(agendaItemUiId)?.toAgendaItemTaskUi()
                                ?: return@launch
                        _state.update {
                            it.copy(
                                editingAgendaItemUi = agendaItem,
                                agendaItemUi = agendaItem,
                                isInEditMode = isInEditMode
                            )
                        }
                    }

                    null -> Unit
                }

            }
        }

    }

    private fun saveAgendaItems() {
        viewModelScope.launch {

            when (val item = state.value.agendaItemUi) {
                is AgendaItemUi.EventUi -> {

                }

                is AgendaItemUi.ReminderUi -> {

                }

                is AgendaItemUi.TaskUi -> {
                    taskRepository.addTask(item.toTask())
                        .onSuccess {
                            // @todo - implement success case handling
                        }
                        .onError { error ->
                            _events.send(AgendaItemDetailsEvent.OnError(error.asUiText()))
                        }
                }

                null -> Unit
            }

        }
    }

    companion object {

        const val AGENDA_ITEM_UI_ID = "agendaItemUiId"
        const val AGENDA_ITEM_UI_TYPE = "agendaItemUiType"
        const val SELECTED_DATE = "selectedDate"
        const val IS_IN_EDIT_MODE = "inInEditMode"

    }
}