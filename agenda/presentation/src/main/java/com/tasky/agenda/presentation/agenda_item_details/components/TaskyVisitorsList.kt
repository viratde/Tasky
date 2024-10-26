package com.tasky.agenda.presentation.agenda_item_details.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasky.agenda.presentation.R
import com.tasky.agenda.presentation.agenda_item_details.components.utils.VisitorsFilterState
import com.tasky.agenda.presentation.agenda_item_details.components.utils.asUiText
import com.tasky.agenda.domain.model.Attendee
import com.tasky.agenda.domain.model.FakeAttendee
import com.tasky.core.presentation.designsystem.ui.TaskyBlack
import com.tasky.core.presentation.designsystem.ui.TaskyDarkGrey
import com.tasky.core.presentation.designsystem.ui.TaskyGrey
import com.tasky.core.presentation.designsystem.ui.TaskyLight2
import com.tasky.core.presentation.designsystem.ui.TaskyTheme
import com.tasky.core.presentation.designsystem.ui.TaskyWhite
import com.tasky.core.presentation.designsystem.ui.inter

@Composable
fun TaskyVisitorsList(
    selectedVisitorsFilterState: VisitorsFilterState,
    onVisitorsFilterStateChange: (VisitorsFilterState) -> Unit,
    visitors: List<Attendee>,
    hostUserId: String,
    onToggleAddModel: () -> Unit,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
    onDelete: (Attendee) -> Unit
) {

    val opacity by animateFloatAsState(targetValue = if (isEnabled) 1f else 0f, label = "")

    Column(
        modifier = modifier,
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = stringResource(id = R.string.visitors),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TaskyBlack,
                    fontFamily = inter,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )

            IconButton(
                onClick = onToggleAddModel,
                enabled = isEnabled,
                modifier = Modifier
                    .graphicsLayer {
                        alpha = opacity
                    },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = TaskyLight2
                ),
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "",
                    tint = TaskyGrey
                )
            }

        }


        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            VisitorsFilterState.entries.map { visitorsFilterState ->
                TaskyFilterChip(
                    text = visitorsFilterState.asUiText().asText(),
                    selected = visitorsFilterState == selectedVisitorsFilterState,
                    onClick = {
                        onVisitorsFilterStateChange(visitorsFilterState)
                    }
                )
            }

        }

        when (selectedVisitorsFilterState) {
            VisitorsFilterState.ALL -> {
                TaskyVisitors(
                    visitors = visitors.filter { it.isGoing },
                    creatorUserId = hostUserId,
                    title = stringResource(id = R.string.going),
                    onDelete = onDelete
                )
                TaskyVisitors(
                    visitors = visitors.filter { !it.isGoing },
                    creatorUserId = hostUserId,
                    title = stringResource(id = R.string.not_going),
                    onDelete = onDelete
                )
            }

            VisitorsFilterState.GOING -> {
                TaskyVisitors(
                    visitors = visitors.filter { it.isGoing },
                    creatorUserId = hostUserId,
                    title = stringResource(id = R.string.going),
                    onDelete = onDelete
                )
            }

            VisitorsFilterState.NOT_GOING -> {
                TaskyVisitors(
                    visitors = visitors.filter { !it.isGoing },
                    creatorUserId = hostUserId,
                    title = stringResource(id = R.string.not_going),
                    onDelete = onDelete
                )
            }
        }

    }

}

@Composable
private fun TaskyVisitors(
    visitors: List<Attendee>,
    creatorUserId: String,
    title: String,
    onDelete: (Attendee) -> Unit
) {

    if (visitors.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 12.dp
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TaskyDarkGrey,
                    fontWeight = FontWeight.Medium,
                    fontFamily = inter,
                    fontSize = 16.sp
                )
            )
            visitors.map { attendee ->
                TaskyVisitor(
                    modifier = Modifier
                        .fillMaxWidth(),
                    name = attendee.fullName,
                    isCreator = attendee.userId == creatorUserId,
                    onDelete = {
                        onDelete(attendee)
                    }
                )
            }
        }
    }


}

@Composable
private fun RowScope.TaskyFilterChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = selected,
        colors = FilterChipDefaults.filterChipColors(
            containerColor = TaskyLight2,
            labelColor = TaskyDarkGrey,
            disabledContainerColor = TaskyLight2,
            disabledLabelColor = TaskyDarkGrey,
            selectedContainerColor = TaskyBlack,
            selectedLabelColor = TaskyWhite,
        ),
        shape = RoundedCornerShape(50),
        onClick = onClick,
        label = {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = inter,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )
        },
        modifier = Modifier
            .weight(1f)
    )
}

@Preview(
    showBackground = true
)
@Composable
private fun TaskyVisitorsListPreview() {
    TaskyTheme {
        TaskyVisitorsList(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            selectedVisitorsFilterState = VisitorsFilterState.ALL,
            onVisitorsFilterStateChange = {

            },
            visitors = listOf(
                FakeAttendee,
                FakeAttendee,
                FakeAttendee,
                FakeAttendee,
                FakeAttendee,
                FakeAttendee,
                FakeAttendee,

                ),
            hostUserId = "",
            onToggleAddModel = {

            },
            isEnabled = true,
            onDelete = {

            }
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun TaskyDisabledVisitorsListPreview() {
    TaskyTheme {
        TaskyVisitorsList(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            selectedVisitorsFilterState = VisitorsFilterState.ALL,
            onVisitorsFilterStateChange = {

            },
            visitors = listOf(
                FakeAttendee,
                FakeAttendee,
                FakeAttendee,
                FakeAttendee,
                FakeAttendee,
                FakeAttendee,
                FakeAttendee,

                ),
            hostUserId = "",
            onToggleAddModel = {

            },
            isEnabled = false,
            onDelete = {

            }
        )
    }
}