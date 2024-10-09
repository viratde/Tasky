package com.tasky.agenda.presentation.event_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tasky.agenda.presentation.R
import com.tasky.agenda.presentation.common.InputType
import com.tasky.agenda.presentation.common.TaskyDateTimePicker
import com.tasky.agenda.presentation.common.TaskyModeledTextField
import com.tasky.agenda.presentation.common.TaskyTitle
import com.tasky.agenda.presentation.common.TaskyTopBar
import com.tasky.agenda.presentation.event_details.model.FakeEventUi
import com.tasky.core.presentation.designsystem.components.LoadingContainer
import com.tasky.core.presentation.designsystem.components.TaskyScaffold
import com.tasky.core.presentation.designsystem.ui.TaskyGrey
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

    LoadingContainer(isLoading = viewModel.state.eventUi == null) {

        EventScreen(
            state = viewModel.state,
            selectedDate = selectedDate,
            onAction = {

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

    requireNotNull(value = state.eventUi)
    TaskyScaffold { innerPadding ->

        Column(
            modifier = Modifier
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
                onEnableEditing = { /*TODO*/ },
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
                    text = state.eventUi.title,
                    inputType = InputType.TITLE,
                    onValueChange = { title ->

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
                    text = state.eventUi.description,
                    inputType = InputType.DESCRIPTION,
                    onValueChange = { desc ->

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
                    title = stringResource(id = R.string.from),
                    dateTime = state.eventUi.from,
                    onChange = { from ->

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
                    dateTime = state.eventUi.to,
                    onChange = { to ->

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
                eventUi = FakeEventUi
            ),
            selectedDate = ZonedDateTime.now().toInstant().toEpochMilli()
        ) {

        }
    }
}