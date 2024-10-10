package com.tasky.agenda.presentation.event_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.tasky.agenda.presentation.event_details.model.AgendaItemUi
import com.tasky.agenda.presentation.event_details.model.FakeEventUi

class EventDetailsViewModel : ViewModel() {

    var state by mutableStateOf(
        EventDetailsState(
            agendaItemUi = FakeEventUi
        )
    )
        private set


    fun onAction(action: EventDetailsAction) {
        when (action) {
            is EventDetailsAction.OnAtChange -> {
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

            is EventDetailsAction.OnDescriptionChange -> {
                state = state.copy(
                    agendaItemUi = state.agendaItemUi?.copy(
                        description = action.description
                    )
                )
            }

            is EventDetailsAction.OnFromChange -> {
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

            is EventDetailsAction.OnRemindTimeChange -> {
                state = state.copy(
                    agendaItemUi = state.agendaItemUi?.copy(
                        remindAt = action.remindTime
                    )
                )
            }

            is EventDetailsAction.OnTitleChange -> {
                state = state.copy(
                    agendaItemUi = state.agendaItemUi?.copy(
                        title = action.title
                    )
                )
            }

            is EventDetailsAction.OnToChange -> {
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

            EventDetailsAction.OnToggleEditMode -> {
                state = state.copy(
                    isInEditMode = !state.isInEditMode
                )
            }
        }
    }

}