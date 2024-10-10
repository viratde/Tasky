package com.tasky.agenda.presentation.event_details

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tasky.agenda.presentation.R
import com.tasky.agenda.presentation.event_details.components.utils.InputType
import com.tasky.agenda.presentation.event_details.components.TaskyDateTimePicker
import com.tasky.agenda.presentation.event_details.components.TaskyModeledTextField
import com.tasky.agenda.presentation.event_details.components.TaskyRemindTimeInput
import com.tasky.agenda.presentation.event_details.components.TaskyTitle
import com.tasky.agenda.presentation.event_details.components.TaskyTopBar
import com.tasky.agenda.presentation.event_details.components.TaskyVisitorsList
import com.tasky.agenda.presentation.event_details.model.AgendaItemUi
import com.tasky.agenda.presentation.event_details.model.FakeEventUi
import com.tasky.core.presentation.designsystem.components.LoadingContainer
import com.tasky.core.presentation.designsystem.components.TaskyScaffold
import com.tasky.core.presentation.designsystem.ui.TaskyLight
import com.tasky.core.presentation.designsystem.ui.TaskyLightGreen
import com.tasky.core.presentation.designsystem.ui.TaskyTheme
import com.tasky.core.presentation.designsystem.ui.TaskyWhite
import java.time.ZonedDateTime

@Composable
fun EventScreenRoot(
    viewModel: EventDetailsViewModel,
    selectedDate: Long
) {

    LoadingContainer(
        modifier = Modifier
            .fillMaxSize(),
        isLoading = viewModel.state.agendaItemUi == null
    ) {
        EventScreen(
            state = viewModel.state,
            selectedDate = selectedDate,
            onAction = { action ->
                viewModel.onAction(action)
            }
        )
    }


}

@Composable
fun EventScreen(
    state: EventDetailsState,
    selectedDate: Long,
    onAction: (EventDetailsAction) -> Unit
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
                onCancel = { /*TODO*/ },
                onEnableEditing = {
                    onAction(EventDetailsAction.OnToggleEditMode)
                },
                onSave = {

                }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(
                        RoundedCornerShape(
                            topEnd = 30.dp,
                            topStart = 30.dp
                        )
                    )
                    .background(TaskyWhite)
                    .verticalScroll(rememberScrollState())
            ) {

                Spacer(modifier = Modifier.height(32.dp))

                TaskyTitle(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    title = stringResource(id = R.string.event),
                    color = TaskyLightGreen
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
                        onAction(EventDetailsAction.OnTitleChange(title))
                    }
                )

                HorizontalDivider(
                    color = TaskyLight,
                    modifier = Modifier
                        .padding(
                            vertical = 16.dp,
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
                        onAction(EventDetailsAction.OnDescriptionChange(desc))
                    }
                )

                HorizontalDivider(
                    color = TaskyLight,
                    modifier = Modifier
                        .padding(
                            vertical = 16.dp,
                            horizontal = 16.dp
                        )
                )

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
                                onAction(EventDetailsAction.OnFromChange(from))
                            }
                        )

                        HorizontalDivider(
                            color = TaskyLight,
                            modifier = Modifier
                                .padding(
                                    vertical = 16.dp,
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
                                onAction(EventDetailsAction.OnFromChange(to))
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
                                onAction(EventDetailsAction.OnAtChange(at))
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
                                onAction(EventDetailsAction.OnAtChange(at))
                            }
                        )
                    }
                }


                HorizontalDivider(
                    color = TaskyLight,
                    modifier = Modifier
                        .padding(
                            vertical = 16.dp,
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
                    onAction(EventDetailsAction.OnRemindTimeChange(remindTime))
                }

                HorizontalDivider(
                    color = TaskyLight,
                    modifier = Modifier
                        .padding(
                            vertical = 16.dp,
                            horizontal = 16.dp
                        )
                )

                if (state.agendaItemUi is AgendaItemUi.EventUi) {
                    TaskyVisitorsList(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 16.dp
                            ),
                        selectedVisitorsFilterState = state.selectedVisitorsFilterState,
                        onVisitorsFilterStateChange = {

                        },
                        visitors = state.agendaItemUi.attendees,
                        hostUserId = "",
                        onToggleAddModel = { /*TODO*/ },
                        isEnabled = state.isInEditMode
                    )
                }

            }

        }

    }

}

@Preview
@Composable
private fun EventScreenPreview() {
    TaskyTheme {
        EventScreen(
            state = EventDetailsState(
                agendaItemUi = FakeEventUi
            ),
            selectedDate = ZonedDateTime.now().toInstant().toEpochMilli()
        ) {

        }
    }
}