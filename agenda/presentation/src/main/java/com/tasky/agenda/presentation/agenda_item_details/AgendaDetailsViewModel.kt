package com.tasky.agenda.presentation.agenda_item_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasky.agenda.domain.model.AgendaPhoto
import com.tasky.agenda.domain.model.Event
import com.tasky.agenda.domain.repository.EventRepository
import com.tasky.agenda.domain.repository.ReminderRepository
import com.tasky.agenda.domain.repository.TaskRepository
import com.tasky.agenda.domain.utils.ConnectivityObserver
import com.tasky.agenda.domain.utils.ImageCompressor
import com.tasky.agenda.presentation.agenda_item_details.components.utils.RemindTimes
import com.tasky.agenda.presentation.agenda_item_details.model.VisitorState
import com.tasky.agenda.presentation.common.mappers.toAgendaItem
import com.tasky.agenda.presentation.common.mappers.toAttendee
import com.tasky.agenda.presentation.common.mappers.toEvent
import com.tasky.agenda.presentation.common.mappers.toReminder
import com.tasky.agenda.presentation.common.mappers.toTask
import com.tasky.agenda.presentation.common.model.AgendaItem
import com.tasky.agenda.presentation.common.util.AgendaItemType
import com.tasky.agenda.presentation.common.util.ifEventUi
import com.tasky.agenda.presentation.common.util.ifReminderUi
import com.tasky.agenda.presentation.common.util.ifTaskUi
import com.tasky.core.domain.AuthInfoStorage
import com.tasky.core.domain.util.Result
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
    private val reminderRepository: ReminderRepository,
    private val authInfoStorage: AuthInfoStorage,
    private val imageCompressor: ImageCompressor,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {


    private val _state = MutableStateFlow(AgendaDetailsState())
    val state = _state
        .onStart {
            loadAgendaItem()
            observeNetworkConnectivity()
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
                _state.value.agendaItem
                    ?.ifTaskUi { taskUi ->
                        _state.update {
                            it.copy(
                                agendaItem = taskUi.copy(
                                    time = action.at
                                )
                            )
                        }
                    }
                    ?.ifReminderUi { reminderUi ->
                        _state.update {
                            it.copy(
                                agendaItem = reminderUi.copy(
                                    time = action.at
                                )
                            )
                        }
                    }
            }

            is AgendaItemDetailsAction.OnDescriptionChange -> {
                _state.update {
                    it.copy(
                        agendaItem = it.agendaItem?.copy(
                            description = action.description
                        )
                    )
                }
            }

            is AgendaItemDetailsAction.OnFromChange -> {
                _state.value.agendaItem
                    ?.ifEventUi { eventUi ->
                        _state.update {
                            it.copy(
                                agendaItem = eventUi.copy(
                                    from = action.from
                                )
                            )
                        }
                    }
            }

            is AgendaItemDetailsAction.OnRemindTimeChange -> {
                _state.update {
                    it.copy(
                        agendaItem = it.agendaItem?.copy(
                            remindAt = action.remindTime
                        )
                    )
                }
            }

            is AgendaItemDetailsAction.OnTitleChange -> {
                _state.update {
                    it.copy(
                        agendaItem = it.agendaItem?.copy(
                            title = action.title
                        )
                    )
                }
            }

            is AgendaItemDetailsAction.OnToChange -> {
                _state.value.agendaItem
                    ?.ifEventUi { eventUi ->
                        _state.update {
                            it.copy(
                                agendaItem = eventUi.copy(
                                    to = action.to
                                )
                            )
                        }
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
                _state.value.agendaItem
                    ?.ifEventUi { eventUi ->
                        viewModelScope.launch {
                            _state.update {
                                it.copy(
                                    agendaItem = eventUi.copy(
                                        photos = eventUi.photos + AgendaPhoto.LocalPhoto(
                                            id = action.photo.id,
                                            photo = imageCompressor.compress(
                                                image = action.photo.photo,
                                                compressionThreshold = IMAGE_SIZE,
                                                mimeType = action.mimeType
                                            )
                                        )
                                    )
                                )
                            }
                        }
                    }
            }

            is AgendaItemDetailsAction.OnAddVisitor -> {
                addVisitor(action.email)
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

            is AgendaItemDetailsAction.OnDeleteVisitor -> {
                _state.value.agendaItem
                    ?.ifEventUi { eventUi ->
                        _state.update {
                            it.copy(
                                agendaItem = eventUi.copy(
                                    attendees = eventUi.attendees.filter {
                                        it.userId != action.attendee.userId || it.userId == eventUi.hostId
                                    }
                                )
                            )
                        }
                    }
            }

            AgendaItemDetailsAction.OnSaveAgendaItem -> {
                saveAgendaItems()
            }

            is AgendaItemDetailsAction.OnDeleteAgendaPhoto -> {
                _state.value.agendaItem
                    ?.ifEventUi { eventUi ->
                        _state.update {
                            when (action.agendaPhoto) {
                                is AgendaPhoto.LocalPhoto -> {
                                    it.copy(
                                        agendaItem = eventUi.copy(
                                            photos = eventUi.photos.filter { it.id != action.agendaPhoto.id }
                                        )
                                    )
                                }

                                is AgendaPhoto.RemotePhoto -> {
                                    it.copy(
                                        agendaItem = eventUi.copy(
                                            photos = eventUi.photos.filter { it.id != action.agendaPhoto.id }
                                        ),
                                        deletedPhotoKeys = it.deletedPhotoKeys + action.agendaPhoto.id
                                    )
                                }
                            }
                        }
                    }
            }

            is AgendaItemDetailsAction.OnDeleteAgendaItem -> {
                deleteAgendaItemUi(action.agendaItem)
            }

            is AgendaItemDetailsAction.OnLeaveAgendaItemEventIi -> {
                deleteLocalAttendeeFromEvent(action.eventUi)
            }

            AgendaItemDetailsAction.OnNavigateUp -> Unit

            is AgendaItemDetailsAction.OnToggleAgendaItemEventIi -> {
                _state.update {
                    it.copy(
                        isGoingIfEventUi = !it.isGoingIfEventUi
                    )
                }
            }
        }


    }


    private fun deleteLocalAttendeeFromEvent(agendaItem: AgendaItem.EventUi) {
        viewModelScope.launch {
            eventRepository.deleteLocalAttendeeFromEvent(agendaItem.toEvent())
            _events.send(AgendaItemDetailsEvent.OnNavigateUp)
        }
    }

    private fun addVisitor(email: String) {
        _state.value.agendaItem
            ?.ifEventUi { eventUi ->
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            visitorState = it.visitorState?.copy(isLoading = true)
                        )
                    }
                    val attendeeResult = eventRepository.getAttendee(email)
                    _state.update {
                        it.copy(
                            visitorState = it.visitorState?.copy(isLoading = false)
                        )
                    }
                    when (attendeeResult) {
                        is Result.Error -> {
                            _events.send(AgendaItemDetailsEvent.OnError(attendeeResult.error.asUiText()))
                        }

                        is Result.Success -> {
                            if (attendeeResult.data != null) {
                                _state.update {
                                    it.copy(
                                        agendaItem = eventUi.copy(
                                            attendees = eventUi.attendees + attendeeResult.data!!.toAttendee(
                                                remindAt = eventUi.to - eventUi.remindAt.getTimeInMilliseconds(),
                                                eventId = eventUi.id
                                            )
                                        ),
                                        visitorState = null
                                    )
                                }
                            }
                        }
                    }
                }
            }
    }

    private fun observeNetworkConnectivity() {
        viewModelScope.launch {
            connectivityObserver.observe().collect { isNetworkConnected ->
                _state.update {
                    it.copy(
                        isNetworkConnected = isNetworkConnected
                    )
                }
            }
        }
    }

    private fun loadAgendaItem() {

        val agendaItemType = savedStateHandle.get<AgendaItemType>(AGENDA_ITEM_UI_TYPE)
        val agendaItemUiId = savedStateHandle.get<String>(AGENDA_ITEM_UI_ID)
        val selectedDate =
            savedStateHandle.get<Long?>(SELECTED_DATE)?.withCurrentTimeHourAndMinutes()
                ?: Instant.now().toEpochMilli()
        val isInEditMode = savedStateHandle[IS_IN_EDIT_MODE] ?: false
        if (agendaItemUiId == null) {
            when (agendaItemType) {
                AgendaItemType.Reminder -> {
                    _state.update {
                        it.copy(
                            agendaItem = AgendaItem.ReminderUi(
                                id = UUID.randomUUID().toString(),
                                title = "New Reminder",
                                description = "Reminder Description",
                                remindAt = RemindTimes.TEN_MINUTES,
                                time = selectedDate
                            ),
                            isInEditMode = true,
                            isCreatorOfPreAgendaItem = true
                        )
                    }
                }

                AgendaItemType.Event -> {
                    viewModelScope.launch {
                        _state.update {
                            it.copy(
                                agendaItem = AgendaItem.EventUi(
                                    id = UUID.randomUUID().toString(),
                                    title = "New Event",
                                    description = "Event Description",
                                    remindAt = RemindTimes.TEN_MINUTES,
                                    from = selectedDate,
                                    to = Instant.ofEpochMilli(selectedDate)
                                        .plus(10, ChronoUnit.MINUTES)
                                        .toEpochMilli(),
                                    attendees = listOf(),
                                    photos = listOf(),
                                    isHost = true,
                                    hostId = authInfoStorage.get()?.userId ?: ""
                                ),
                                isInEditMode = true,
                                isCreatorOfPreAgendaItem = true
                            )
                        }
                    }
                }

                AgendaItemType.Task -> {
                    _state.update {
                        it.copy(
                            agendaItem = AgendaItem.TaskUi(
                                id = UUID.randomUUID().toString(),
                                title = "New Task",
                                description = "Task Description",
                                remindAt = RemindTimes.TEN_MINUTES,
                                time = selectedDate,
                                isDone = false
                            ),
                            isInEditMode = true,
                            isCreatorOfPreAgendaItem = true
                        )
                    }
                }

                null -> Unit
            }

        } else {
            viewModelScope.launch {
                when (agendaItemType) {
                    AgendaItemType.Reminder -> {
                        val agendaItem = reminderRepository.getReminderById(agendaItemUiId)
                            ?.toAgendaItem() ?: return@launch
                        _state.update {
                            it.copy(
                                isEditingPreAgendaItem = true,
                                agendaItem = agendaItem,
                                isInEditMode = isInEditMode,
                                isCreatorOfPreAgendaItem = true
                            )
                        }
                    }

                    AgendaItemType.Event -> {
                        val event = eventRepository.getEventById(agendaItemUiId) ?: return@launch
                        val agendaItem = event.toAgendaItem()
                        _state.update {
                            it.copy(
                                isEditingPreAgendaItem = true,
                                agendaItem = agendaItem,
                                isInEditMode = isInEditMode,
                                isCreatorOfPreAgendaItem = event.isUserEventCreator
                            )
                        }
                    }

                    AgendaItemType.Task -> {
                        val agendaItem =
                            taskRepository.getTaskById(agendaItemUiId)?.toAgendaItem()
                                ?: return@launch
                        _state.update {
                            it.copy(
                                isEditingPreAgendaItem = true,
                                agendaItem = agendaItem,
                                isInEditMode = isInEditMode,
                                isCreatorOfPreAgendaItem = true
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
            if (_state.value.isEditingPreAgendaItem) {
                _state.value.agendaItem
                    ?.ifEventUi {
                        _state.update { it.copy(isSaving = true) }
                        eventRepository.updateEvent(it.toEvent(), _state.value.deletedPhotoKeys)
                            .onSuccess {
                                _events.send(AgendaItemDetailsEvent.OnNavigateUp)
                                _state.update { it.copy(isSaving = false) }
                            }
                            .onError { error ->
                                _events.send(AgendaItemDetailsEvent.OnError(error.asUiText()))
                                _state.update { it.copy(isSaving = false) }
                            }
                    }
                    ?.ifTaskUi {
                        _state.update { it.copy(isSaving = true) }
                        taskRepository.updateTask(it.toTask())
                            .onSuccess {
                                _events.send(AgendaItemDetailsEvent.OnNavigateUp)
                                _state.update { it.copy(isSaving = false) }
                            }
                            .onError { error ->
                                _events.send(AgendaItemDetailsEvent.OnError(error.asUiText()))
                                _state.update { it.copy(isSaving = false) }
                            }
                    }
                    ?.ifReminderUi {
                        _state.update { it.copy(isSaving = true) }
                        reminderRepository.updateReminder(it.toReminder())
                            .onSuccess {
                                _events.send(AgendaItemDetailsEvent.OnNavigateUp)
                                _state.update { it.copy(isSaving = false) }
                            }
                            .onError { error ->
                                _events.send(AgendaItemDetailsEvent.OnError(error.asUiText()))
                                _state.update { it.copy(isSaving = false) }
                            }

                    }
            } else {
                _state.value.agendaItem
                    ?.ifEventUi {
                        _state.update { it.copy(isSaving = true) }
                        eventRepository.addEvent(it.toEvent())
                            .onSuccess {
                                _events.send(AgendaItemDetailsEvent.OnNavigateUp)
                                _state.update { it.copy(isSaving = false) }
                            }
                            .onError { error ->
                                _events.send(AgendaItemDetailsEvent.OnError(error.asUiText()))
                                _state.update { it.copy(isSaving = false) }
                            }
                    }
                    ?.ifTaskUi {
                        _state.update { it.copy(isSaving = true) }
                        taskRepository.addTask(it.toTask())
                            .onSuccess {
                                _events.send(AgendaItemDetailsEvent.OnNavigateUp)
                                _state.update { it.copy(isSaving = false) }
                            }
                            .onError { error ->
                                _events.send(AgendaItemDetailsEvent.OnError(error.asUiText()))
                                _state.update { it.copy(isSaving = false) }
                            }
                    }
                    ?.ifReminderUi {
                        _state.update { it.copy(isSaving = true) }
                        reminderRepository.addReminder(it.toReminder())
                            .onSuccess {
                                _events.send(AgendaItemDetailsEvent.OnNavigateUp)
                                _state.update { it.copy(isSaving = false) }
                            }
                            .onError { error ->
                                _events.send(AgendaItemDetailsEvent.OnError(error.asUiText()))
                                _state.update { it.copy(isSaving = false) }
                            }

                    }
            }
        }
    }


    private fun deleteAgendaItemUi(
        agendaItemUi: AgendaItem
    ) {
        viewModelScope.launch {
            agendaItemUi
                .ifEventUi {
                    eventRepository.deleteEventById(agendaItemUi.id)
                    _events.send(AgendaItemDetailsEvent.OnNavigateUp)
                }
                .ifTaskUi {
                    taskRepository.deleteTaskById(agendaItemUi.id)
                    _events.send(AgendaItemDetailsEvent.OnNavigateUp)
                }
                .ifReminderUi {
                    reminderRepository.deleteRemindersById(agendaItemUi.id)
                    _events.send(AgendaItemDetailsEvent.OnNavigateUp)
                }

        }
    }

    companion object {

        const val AGENDA_ITEM_UI_ID = "agendaItemUiId"
        const val AGENDA_ITEM_UI_TYPE = "agendaItemUiType"
        const val SELECTED_DATE = "selectedDate"
        const val IS_IN_EDIT_MODE = "inInEditMode"
        const val IMAGE_SIZE = 200 * 1024L

    }
}