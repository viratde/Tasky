package com.tasky.agenda.presentation.agenda.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasky.agenda.presentation.R
import com.tasky.agenda.presentation.common.model.AgendaItemUi
import com.tasky.agenda.presentation.common.model.FakeEventUi
import com.tasky.agenda.presentation.common.model.FakeRemainderUi
import com.tasky.agenda.presentation.common.model.FakeTaskUi
import com.tasky.core.presentation.designsystem.ui.ContextMenuIcon
import com.tasky.core.presentation.designsystem.ui.TaskyBlack
import com.tasky.core.presentation.designsystem.ui.TaskyBlue
import com.tasky.core.presentation.designsystem.ui.TaskyDarkGrey
import com.tasky.core.presentation.designsystem.ui.TaskyGreen
import com.tasky.core.presentation.designsystem.ui.TaskyGrey
import com.tasky.core.presentation.designsystem.ui.TaskyLight
import com.tasky.core.presentation.designsystem.ui.TaskyLight2
import com.tasky.core.presentation.designsystem.ui.TaskyLightGreen
import com.tasky.core.presentation.designsystem.ui.TaskyTheme
import com.tasky.core.presentation.designsystem.ui.TaskyWhite
import com.tasky.core.presentation.designsystem.ui.inter
import com.tasky.core.presentation.ui.toFormatAgendaUiDate


@Composable
fun AgendaItemUi(
    agendaItemUi: AgendaItemUi,
    modifier: Modifier = Modifier,
    onEdit: () -> Unit = {},
    onView: () -> Unit = {},
    onDelete: () -> Unit = {},
    onToggle: () -> Unit = {},
) {

    var isDropDownMenuOpen by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                when (agendaItemUi) {
                    is AgendaItemUi.EventUi -> TaskyLightGreen
                    is AgendaItemUi.ReminderUi -> TaskyLight2
                    is AgendaItemUi.TaskUi -> TaskyGreen
                }
            )
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp
            )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .clickable(agendaItemUi is AgendaItemUi.TaskUi) {
                        onToggle()
                    }
                    .border(
                        2.dp,
                        when (agendaItemUi) {
                            is AgendaItemUi.EventUi -> TaskyBlack
                            is AgendaItemUi.ReminderUi -> TaskyBlack
                            is AgendaItemUi.TaskUi -> TaskyWhite
                        },
                        CircleShape
                    )
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = agendaItemUi.title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = when (agendaItemUi) {
                        is AgendaItemUi.EventUi -> TaskyBlack
                        is AgendaItemUi.ReminderUi -> TaskyBlack
                        is AgendaItemUi.TaskUi -> TaskyWhite
                    },
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = inter
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Box {
                IconButton(
                    onClick = { isDropDownMenuOpen = !isDropDownMenuOpen }
                ) {
                    Icon(
                        imageVector = ContextMenuIcon,
                        contentDescription = null,
                        tint = when (agendaItemUi) {
                            is AgendaItemUi.EventUi -> TaskyBlack
                            is AgendaItemUi.ReminderUi -> TaskyBlack
                            is AgendaItemUi.TaskUi -> TaskyWhite
                        }
                    )
                }
                AgendaItemUiContextMenu(
                    expanded = isDropDownMenuOpen,
                    onClose = {
                        isDropDownMenuOpen = false
                    },
                    onEdit = onEdit,
                    onView = onView,
                    onDelete = onDelete
                )
            }

        }


        Text(
            text = agendaItemUi.description,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = when (agendaItemUi) {
                    is AgendaItemUi.EventUi -> TaskyDarkGrey
                    is AgendaItemUi.ReminderUi -> TaskyDarkGrey
                    is AgendaItemUi.TaskUi -> TaskyWhite
                },
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = inter
            ),
            modifier = Modifier
                .padding(start = 28.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = when (agendaItemUi) {
                is AgendaItemUi.EventUi -> "${agendaItemUi.from.toFormatAgendaUiDate()} - ${agendaItemUi.to.toFormatAgendaUiDate()}"
                is AgendaItemUi.ReminderUi -> agendaItemUi.time.toFormatAgendaUiDate()
                is AgendaItemUi.TaskUi -> agendaItemUi.time.toFormatAgendaUiDate()
            },
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                fontFamily = inter,
                color = when (agendaItemUi) {
                    is AgendaItemUi.EventUi -> TaskyDarkGrey
                    is AgendaItemUi.ReminderUi -> TaskyDarkGrey
                    is AgendaItemUi.TaskUi -> TaskyWhite
                },
                textAlign = TextAlign.Right
            ),
            modifier = Modifier
                .fillMaxWidth()
        )

    }

}

@Composable
private fun AgendaItemUiContextMenu(
    expanded: Boolean,
    onClose: () -> Unit,
    onEdit: () -> Unit,
    onView: () -> Unit,
    onDelete: () -> Unit
) {

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onClose,
        containerColor = TaskyWhite,
        shape = RoundedCornerShape(10.dp),
    ) {

        AgendaItemUiContextMenuItem(
            label = stringResource(id = R.string.open),
            onClick = onView
        )

        HorizontalDivider(
            color = TaskyLight
        )
        AgendaItemUiContextMenuItem(
            label = stringResource(id = R.string.edit),
            onClick = onEdit
        )
        HorizontalDivider(
            color = TaskyLight
        )
        AgendaItemUiContextMenuItem(
            label = stringResource(id = R.string.delete),
            onClick = onDelete
        )

    }

}

@Composable
private fun AgendaItemUiContextMenuItem(
    label: String,
    onClick: () -> Unit
) {

    DropdownMenuItem(
        text = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TaskyBlack,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = inter
                )
            )
        },
        onClick = onClick
    )

}

@Preview(
    showBackground = true
)
@Composable
private fun BasicAgendaItemUiPreview() {
    TaskyTheme {
        AgendaItemUi(
            agendaItemUi = FakeEventUi,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun BasicAgendaItemTaskUiPreview() {
    TaskyTheme {
        AgendaItemUi(
            agendaItemUi = FakeTaskUi,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun BasicAgendaItemRemainderUiPreview() {
    TaskyTheme {
        AgendaItemUi(
            agendaItemUi = FakeRemainderUi,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}