package com.tasky.agenda.presentation.agenda_item_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.tasky.agenda.presentation.common.model.AgendaItemUi
import com.tasky.agenda.presentation.common.model.FakeEventUi
import com.tasky.agenda.presentation.agenda_item_details.model.VisitorState

class AgendaDetailsViewModel : ViewModel() {

    var state by mutableStateOf(
        AgendaDetailsState(
            agendaItemUi = FakeEventUi
        )
    )
        private set


    fun onAction(action: AgendaItemDetailsAction) {
        when (action) {
            is AgendaItemDetailsAction.OnAtChange -> {
                when (val item = state.agendaItemUi) {
                    is AgendaItemUi.ReminderUi -> {
                        state = state.copy(
                            agendaItemUi = item.copy(
                                time = action.at
                            )
                        )
                    }

                    is AgendaItemUi.TaskUi -> {
                        state = state.copy(
                            agendaItemUi = item.copy(
                                time = action.at
                            )
                        )
                    }

                    else -> Unit
                }
            }

            is AgendaItemDetailsAction.OnDescriptionChange -> {
                state = state.copy(
                    agendaItemUi = state.agendaItemUi?.copy(
                        description = action.description
                    )
                )
            }

            is AgendaItemDetailsAction.OnFromChange -> {
                when (val item = state.agendaItemUi) {
                    is AgendaItemUi.EventUi -> {
                        state = state.copy(
                            agendaItemUi = item.copy(
                                from = action.from
                            )
                        )
                    }

                    else -> Unit
                }
            }

            is AgendaItemDetailsAction.OnRemindTimeChange -> {
                state = state.copy(
                    agendaItemUi = state.agendaItemUi?.copy(
                        remindAt = action.remindTime
                    )
                )
            }

            is AgendaItemDetailsAction.OnTitleChange -> {
                state = state.copy(
                    agendaItemUi = state.agendaItemUi?.copy(
                        title = action.title
                    )
                )
            }

            is AgendaItemDetailsAction.OnToChange -> {
                when (val item = state.agendaItemUi) {
                    is AgendaItemUi.EventUi -> {
                        state = state.copy(
                            agendaItemUi = item.copy(
                                to = action.to
                            )
                        )
                    }

                    else -> Unit
                }
            }

            AgendaItemDetailsAction.OnToggleEditMode -> {
                state = state.copy(
                    isInEditMode = !state.isInEditMode
                )
            }

            is AgendaItemDetailsAction.OnAddAgendaPhoto -> {
                if (state.agendaItemUi is AgendaItemUi.EventUi) {
                    state = state.copy(
                        agendaItemUi = updateDetailsIfEvent {
                            it.copy(
                                photos = it.photos + action.photo
                            )
                        }
                    )
                }
            }

            AgendaItemDetailsAction.OnAddVisitor -> {

            }

            AgendaItemDetailsAction.OnToggleVisitorsModel -> {
                state = state.copy(
                    visitorState = if (state.visitorState == null) VisitorState() else null
                )
            }

            is AgendaItemDetailsAction.OnVisitorsEmailChange -> {
                state = state.copy(
                    visitorState = state.visitorState?.copy(
                        email = action.email,
                        isValidEmail = true
                    )
                )
            }

            is AgendaItemDetailsAction.OnVisitorFilterChange -> {
                state = state.copy(
                    selectedVisitorsFilterState = action.visitorsFilterState
                )
            }
        }
    }

    private fun updateDetailsIfEvent(
        update: (AgendaItemUi.EventUi) -> AgendaItemUi.EventUi
    ): AgendaItemUi? {
        return when (val details = state.agendaItemUi) {
            is AgendaItemUi.EventUi -> update(details)
            else -> details
        }
    }
}