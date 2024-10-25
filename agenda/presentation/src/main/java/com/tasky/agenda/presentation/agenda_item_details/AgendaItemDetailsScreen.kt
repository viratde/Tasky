package com.tasky.agenda.presentation.agenda_item_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tasky.agenda.presentation.R
import com.tasky.agenda.presentation.agenda_item_details.components.utils.InputType
import com.tasky.agenda.presentation.agenda_item_details.components.TaskyDateTimePicker
import com.tasky.agenda.presentation.agenda_item_details.components.TaskyAgendaButton
import com.tasky.agenda.presentation.agenda_item_details.components.TaskyModeledTextField
import com.tasky.agenda.presentation.agenda_item_details.components.TaskyPhotosInput
import com.tasky.agenda.presentation.agenda_item_details.components.TaskyRemindTimeInput
import com.tasky.agenda.presentation.agenda_item_details.components.TaskyTitle
import com.tasky.agenda.presentation.agenda_item_details.components.TaskyTopBar
import com.tasky.agenda.presentation.agenda_item_details.components.TaskyVisitorsAdderInput
import com.tasky.agenda.presentation.agenda_item_details.components.TaskyVisitorsList
import com.tasky.agenda.presentation.common.model.AgendaItemUi
import com.tasky.agenda.presentation.common.model.FakeEventUi
import com.tasky.agenda.presentation.common.model.FakeRemainderUi
import com.tasky.agenda.presentation.common.model.FakeTaskUi
import com.tasky.core.presentation.designsystem.components.LoadingContainer
import com.tasky.core.presentation.designsystem.components.TaskyScaffold
import com.tasky.core.presentation.designsystem.ui.TaskyGreen
import com.tasky.core.presentation.designsystem.ui.TaskyGrey
import com.tasky.core.presentation.designsystem.ui.TaskyLight
import com.tasky.core.presentation.designsystem.ui.TaskyLightGreen
import com.tasky.core.presentation.designsystem.ui.TaskyTheme
import com.tasky.core.presentation.designsystem.ui.TaskyWhite
import com.tasky.core.presentation.ui.ObserverAsEvents
import org.koin.androidx.compose.koinViewModel
import java.time.ZonedDateTime

@Composable
fun AgendaItemDetailsRoot(
    viewModel: AgendaDetailsViewModel = koinViewModel(),
    selectedDate: Long,
    onNavigateUp: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserverAsEvents(viewModel.events) { event ->
        when (event) {
            is AgendaItemDetailsEvent.OnError -> {

            }

            AgendaItemDetailsEvent.OnNavigateUp -> {
                onNavigateUp()
            }
        }
    }

    LoadingContainer(
        modifier = Modifier
            .fillMaxSize(),
        isLoading = state.agendaItemUi == null
    ) {
        AgendaItemDetailsScreen(
            state = state,
            selectedDate = selectedDate,
            onAction = { action ->
                viewModel.onAction(action)
            }
        )
    }


}

@Composable
fun AgendaItemDetailsScreen(
    state: AgendaDetailsState,
    selectedDate: Long,
    onAction: (AgendaItemDetailsAction) -> Unit
) {

    requireNotNull(value = state.agendaItemUi)
    TaskyScaffold { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            TaskyTopBar(
                modifier = Modifier
                    .padding(
                        vertical = 6.dp,
                        horizontal = 12.dp
                    ),
                date = selectedDate,
                isInEditMode = state.isInEditMode,
                onCancel = {

                },
                onEnableEditing = {
                    onAction(AgendaItemDetailsAction.OnToggleEditMode)
                },
                onSave = {
                    onAction(AgendaItemDetailsAction.OnSaveAgendaItem)
                }
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(
                        RoundedCornerShape(
                            topEnd = 30.dp,
                            topStart = 30.dp
                        )
                    )
                    .background(TaskyWhite)
            ) {

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {

                    Spacer(modifier = Modifier.height(32.dp))

                    TaskyTitle(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        title = when (state.agendaItemUi) {
                            is AgendaItemUi.TaskUi -> stringResource(id = R.string.task)
                            is AgendaItemUi.EventUi -> stringResource(id = R.string.event)
                            is AgendaItemUi.ReminderUi -> stringResource(id = R.string.remainder)
                        },
                        color = when (state.agendaItemUi) {
                            is AgendaItemUi.EventUi -> TaskyLightGreen
                            is AgendaItemUi.ReminderUi -> TaskyGrey
                            is AgendaItemUi.TaskUi -> TaskyGreen
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    TaskyModeledTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        placeHolder = stringResource(id = R.string.title),
                        title = stringResource(id = R.string.edit_title),
                        text = state.agendaItemUi.title,
                        inputType = InputType.TITLE,
                        isEnabled = state.isInEditMode,
                        onValueChange = { title ->
                            onAction(AgendaItemDetailsAction.OnTitleChange(title))
                        }
                    )

                    HorizontalDivider(
                        color = TaskyLight,
                        modifier = Modifier
                            .padding(
                                vertical = 12.dp,
                                horizontal = 16.dp
                            )
                    )

                    TaskyModeledTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        placeHolder = stringResource(id = R.string.description),
                        title = stringResource(id = R.string.edit_description),
                        text = state.agendaItemUi.description,
                        inputType = InputType.DESCRIPTION,
                        isEnabled = state.isInEditMode,
                        onValueChange = { desc ->
                            onAction(AgendaItemDetailsAction.OnDescriptionChange(desc))
                        }
                    )

                    HorizontalDivider(
                        color = TaskyLight,
                        modifier = Modifier
                            .padding(
                                vertical = 12.dp,
                                horizontal = 16.dp
                            )
                    )


                    if (state.agendaItemUi is AgendaItemUi.EventUi) {

                        TaskyPhotosInput(
                            modifier = Modifier
                                .fillMaxWidth(),
                            photos = state.agendaItemUi.photos,
                            enabled = state.isInEditMode,
                            onAddPhoto = { photo ->
                                onAction(AgendaItemDetailsAction.OnAddAgendaPhoto(photo))
                            },
                            onDeletePhoto = { photo ->
                                onAction(AgendaItemDetailsAction.OnDeleteAgendaPhoto(photo))
                            }
                        )

                        HorizontalDivider(
                            color = TaskyLight,
                            modifier = Modifier
                                .padding(
                                    vertical = 12.dp,
                                    horizontal = 16.dp
                                )
                        )

                    }

                    when (state.agendaItemUi) {
                        is AgendaItemUi.EventUi -> {
                            TaskyDateTimePicker(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                title = stringResource(id = R.string.from),
                                dateTime = state.agendaItemUi.from,
                                isEnabled = state.isInEditMode,
                                onChange = { from ->
                                    onAction(AgendaItemDetailsAction.OnFromChange(from))
                                }
                            )

                            HorizontalDivider(
                                color = TaskyLight,
                                modifier = Modifier
                                    .padding(
                                        vertical = 12.dp,
                                        horizontal = 16.dp
                                    )
                            )

                            TaskyDateTimePicker(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                title = stringResource(id = R.string.to),
                                dateTime = state.agendaItemUi.to,
                                isEnabled = state.isInEditMode,
                                onChange = { to ->
                                    onAction(AgendaItemDetailsAction.OnFromChange(to))
                                }
                            )
                        }

                        is AgendaItemUi.ReminderUi -> {
                            TaskyDateTimePicker(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                title = stringResource(id = R.string.at),
                                dateTime = state.agendaItemUi.time,
                                isEnabled = state.isInEditMode,
                                onChange = { at ->
                                    onAction(AgendaItemDetailsAction.OnAtChange(at))
                                }
                            )


                        }

                        is AgendaItemUi.TaskUi -> {
                            TaskyDateTimePicker(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                title = stringResource(id = R.string.at),
                                dateTime = state.agendaItemUi.time,
                                isEnabled = state.isInEditMode,
                                onChange = { at ->
                                    onAction(AgendaItemDetailsAction.OnAtChange(at))
                                }
                            )
                        }
                    }


                    HorizontalDivider(
                        color = TaskyLight,
                        modifier = Modifier
                            .padding(
                                vertical = 12.dp,
                                horizontal = 16.dp
                            )
                    )

                    TaskyRemindTimeInput(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        remindTime = state.agendaItemUi.remindAt,
                        isEnabled = state.isInEditMode
                    ) { remindTime ->
                        onAction(AgendaItemDetailsAction.OnRemindTimeChange(remindTime))
                    }

                    HorizontalDivider(
                        color = TaskyLight,
                        modifier = Modifier
                            .padding(
                                vertical = 12.dp,
                                horizontal = 16.dp
                            )
                    )

                    if (state.agendaItemUi is AgendaItemUi.EventUi) {
                        TaskyVisitorsList(
                            modifier = Modifier
                                .padding(
                                    horizontal = 16.dp
                                ),
                            selectedVisitorsFilterState = state.selectedVisitorsFilterState,
                            onVisitorsFilterStateChange = { visitorsFilterState ->
                                onAction(
                                    AgendaItemDetailsAction.OnVisitorFilterChange(
                                        visitorsFilterState
                                    )
                                )
                            },
                            visitors = state.agendaItemUi.attendees,
                            hostUserId =state.agendaItemUi.hostId,
                            onToggleAddModel = {
                                onAction(AgendaItemDetailsAction.OnToggleVisitorsModel)
                            },
                            isEnabled = state.isInEditMode
                        )

                        if (state.visitorState != null) {
                            TaskyVisitorsAdderInput(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 16.dp
                                    ),
                                email = state.visitorState.email,
                                onClose = {
                                    onAction(AgendaItemDetailsAction.OnToggleVisitorsModel)
                                },
                                isValidEmail = state.visitorState.isValidEmail,
                                onAdd = {
                                    onAction(AgendaItemDetailsAction.OnAddVisitor)
                                },
                                isLoading = state.visitorState.isLoading,
                                onEmailChange = { email ->
                                    onAction(AgendaItemDetailsAction.OnVisitorsEmailChange(email))
                                }
                            )
                        }

                        HorizontalDivider(
                            color = TaskyLight,
                            modifier = Modifier
                                .padding(
                                    vertical = 12.dp,
                                    horizontal = 16.dp
                                )
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                }

                Spacer(modifier = Modifier.height(16.dp))

                TaskyAgendaButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = when (state.agendaItemUi) {
                        is AgendaItemUi.EventUi -> {
                            stringResource(id = if (state.agendaItemUi.isHost) R.string.delete_event else R.string.leave_event)
                        }

                        is AgendaItemUi.ReminderUi -> stringResource(id = R.string.delete_remainder)
                        is AgendaItemUi.TaskUi -> stringResource(id = R.string.delete_task)
                    },
                    enabled = true,
                    onClick = {

                    }
                )

                Spacer(modifier = Modifier.height(32.dp))
            }

        }

    }

}

@Preview
@Composable
private fun AgendaEventUiScreenPreview() {
    TaskyTheme {
        AgendaItemDetailsScreen(
            state = AgendaDetailsState(
                agendaItemUi = FakeEventUi
            ),
            selectedDate = ZonedDateTime.now().toInstant().toEpochMilli()
        ) { action ->

        }
    }
}

@Preview
@Composable
private fun AgendaRemainderUiScreenPreview() {
    TaskyTheme {
        AgendaItemDetailsScreen(
            state = AgendaDetailsState(
                agendaItemUi = FakeRemainderUi
            ),
            selectedDate = ZonedDateTime.now().toInstant().toEpochMilli()
        ) { action ->

        }
    }
}

@Preview
@Composable
private fun AgendaTaskUiScreenPreview() {
    TaskyTheme {
        AgendaItemDetailsScreen(
            state = AgendaDetailsState(
                agendaItemUi = FakeTaskUi
            ),
            selectedDate = ZonedDateTime.now().toInstant().toEpochMilli()
        ) { action ->

        }
    }
}