package com.tasky.agenda.presentation.agenda

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tasky.agenda.presentation.agenda.components.AgendaDateLabel
import com.tasky.agenda.presentation.agenda.components.AgendaDateRange
import com.tasky.agenda.presentation.agenda.components.AgendaItemUi
import com.tasky.agenda.presentation.agenda.components.AgendaItemsTopBar
import com.tasky.agenda.presentation.common.model.AgendaItemUi
import com.tasky.agenda.presentation.common.model.FakeEventUi
import com.tasky.agenda.presentation.common.model.FakeRemainderUi
import com.tasky.agenda.presentation.common.model.FakeTaskUi
import com.tasky.core.presentation.designsystem.components.TaskyFloatingActionButton
import com.tasky.core.presentation.designsystem.components.TaskyScaffold
import com.tasky.core.presentation.designsystem.ui.TaskyBlack
import com.tasky.core.presentation.designsystem.ui.TaskyTheme
import com.tasky.core.presentation.designsystem.ui.TaskyWhite


@Composable
fun AgendaItemsScreen(
    state: AgendaItemsState,
    onAction: (AgendaItemsAction) -> Unit
) {


    TaskyScaffold(
        floatingActionButton = {
            TaskyFloatingActionButton(
                onClick = {
                    onAction(AgendaItemsAction.OnToggleAddAgendaItemDropDown)
                },
                containerColor = TaskyBlack
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = TaskyWhite
                )
            }

        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            AgendaItemsTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp
                    ),
                selectedDate = state.selectedDate,
                onToggleDateSelector = {
                    onAction(AgendaItemsAction.OnToggleDateSelectorModel)
                },
                name = state.fullName,
                onToggleLogoutDropDown = {
                    onAction(AgendaItemsAction.OnToggleLogOutDropDown)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

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

                Spacer(modifier = Modifier.height(16.dp))

                AgendaDateRange(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    selectionStartDate = state.selectionStartDate,
                    selectedDate = state.selectedDate,
                    noOfDaysToRender = state.noOfDaysToRender,
                    onSelectionDateChanged = {

                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                AgendaDateLabel(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    selectedDate = state.selectedDate
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.agendaItems) { agendaItemUi ->
                        AgendaItemUi(
                            agendaItemUi = agendaItemUi,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            onDelete = {
                                onAction(AgendaItemsAction.OnDeleteAgendaItemUi(agendaItemUi))
                            },
                            onEdit = {
                                onAction(AgendaItemsAction.OnEditAgendaItemUi(agendaItemUi))
                            },
                            onToggle = if (agendaItemUi is AgendaItemUi.TaskUi) ({
                                onAction(AgendaItemsAction.OnToggleTaskUiCompletion(agendaItemUi))
                            }) else null,
                            onView = {
                                onAction(AgendaItemsAction.OnOpenAgendaItemUi(agendaItemUi))
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

            }

        }

    }

}

@Preview
@Composable
private fun AgendaItemsScreenPreview() {
    TaskyTheme {
        AgendaItemsScreen(
            state = AgendaItemsState(
                fullName = "Virat Kumar",
                agendaItems = listOf(
                    FakeEventUi,
                    FakeRemainderUi,
                    FakeTaskUi,
                    FakeEventUi,
                    FakeRemainderUi,
                    FakeTaskUi,
                    FakeEventUi,
                    FakeRemainderUi,
                    FakeTaskUi,
                )
            )
        ) { action ->

        }
    }
}