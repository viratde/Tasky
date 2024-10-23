package com.tasky.agenda.presentation.agenda_item_details

import androidx.lifecycle.ViewModel
import com.tasky.agenda.presentation.agenda_item_details.model.VisitorState
import com.tasky.agenda.presentation.common.model.AgendaItemUi
import com.tasky.agenda.presentation.common.model.FakeEventUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AgendaDetailsViewModel : ViewModel() {

    private val _state = MutableStateFlow(AgendaDetailsState(agendaItemUi = FakeEventUi))
    val state = _state.asStateFlow()

    fun onAction(action: AgendaItemDetailsAction) {
        when (action) {
            is AgendaItemDetailsAction.OnAtChange -> {
                when (val item = _state.value.agendaItemUi) {
                    is AgendaItemUi.ReminderUi -> {
                        _state.update {
                            it.copy(
                                agendaItemUi = item.copy(
                                    time = action.at
                                )
                            )
                        }
                    }

                    is AgendaItemUi.TaskUi -> {
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
}