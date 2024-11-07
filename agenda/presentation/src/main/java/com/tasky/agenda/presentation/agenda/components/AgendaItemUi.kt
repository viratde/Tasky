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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasky.agenda.presentation.R
import com.tasky.agenda.presentation.common.model.AgendaItem
import com.tasky.agenda.presentation.common.model.AgendaItemUi
import com.tasky.agenda.presentation.common.model.FakeEventUi
import com.tasky.agenda.presentation.common.model.FakeRemainderUi
import com.tasky.agenda.presentation.common.model.FakeTaskUi
import com.tasky.core.presentation.designsystem.components.TaskyDropDownMenu
import com.tasky.core.presentation.designsystem.components.TaskyDropDownMenuItem
import com.tasky.core.presentation.designsystem.ui.CheckedIcon
import com.tasky.core.presentation.designsystem.ui.ContextMenuIcon
import com.tasky.core.presentation.designsystem.ui.TaskyBlack
import com.tasky.core.presentation.designsystem.ui.TaskyDarkGrey
import com.tasky.core.presentation.designsystem.ui.TaskyGreen
import com.tasky.core.presentation.designsystem.ui.TaskyLight
import com.tasky.core.presentation.designsystem.ui.TaskyLight2
import com.tasky.core.presentation.designsystem.ui.TaskyLightGreen
import com.tasky.core.presentation.designsystem.ui.TaskyTheme
import com.tasky.core.presentation.designsystem.ui.TaskyWhite
import com.tasky.core.presentation.designsystem.ui.inter
import com.tasky.core.presentation.ui.toFormatAgendaUiDate


@Composable
fun AgendaItemUi(
    onEdit: () -> Unit,
    onView: () -> Unit,
    onDelete: () -> Unit,
    agendaItem: AgendaItem,
    isContextMenuOpen: Boolean,
    modifier: Modifier = Modifier,
    onToggle: (() -> Unit)? = null,
    onToggleContextMenu: () -> Unit
) {


    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                when (agendaItem) {
                    is AgendaItem.EventUi -> TaskyLightGreen
                    is AgendaItem.ReminderUi -> TaskyLight2
                    is AgendaItem.TaskUi -> TaskyGreen
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
                    .clickable(agendaItem is AgendaItem.TaskUi && onToggle != null) {
                        onToggle?.invoke()
                    }
                    .border(
                        2.dp,
                        when (agendaItem) {
                            is AgendaItem.EventUi -> TaskyBlack
                            is AgendaItem.ReminderUi -> TaskyBlack
                            is AgendaItem.TaskUi -> TaskyWhite
                        },
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (agendaItem is AgendaItem.TaskUi && agendaItem.isDone) {
                    Icon(
                        imageVector = CheckedIcon,
                        contentDescription = null,
                        tint = TaskyWhite,
                        modifier = Modifier
                            .size(10.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = agendaItem.title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = when (agendaItem) {
                        is AgendaItem.EventUi -> TaskyBlack
                        is AgendaItem.ReminderUi -> TaskyBlack
                        is AgendaItem.TaskUi -> TaskyWhite
                    },
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = inter,
                    textDecoration = if (agendaItem is AgendaItem.TaskUi && agendaItem.isDone) TextDecoration.LineThrough else null
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Box {
                IconButton(
                    onClick = onToggleContextMenu
                ) {
                    Icon(
                        imageVector = ContextMenuIcon,
                        contentDescription = null,
                        tint = when (agendaItem) {
                            is AgendaItem.EventUi -> TaskyBlack
                            is AgendaItem.ReminderUi -> TaskyBlack
                            is AgendaItem.TaskUi -> TaskyWhite
                        }
                    )
                }
                AgendaItemContextMenu(
                    expanded = isContextMenuOpen,
                    onClose = onToggleContextMenu,
                    onEdit = onEdit,
                    onView = onView,
                    onDelete = onDelete
                )
            }

        }


        Text(
            text = agendaItem.description,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = when (agendaItem) {
                    is AgendaItem.EventUi -> TaskyDarkGrey
                    is AgendaItem.ReminderUi -> TaskyDarkGrey
                    is AgendaItem.TaskUi -> TaskyWhite
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
            text = when (agendaItem) {
                is AgendaItem.EventUi -> "${agendaItem.from.toFormatAgendaUiDate()} - ${agendaItem.to.toFormatAgendaUiDate()}"
                is AgendaItem.ReminderUi -> agendaItem.time.toFormatAgendaUiDate()
                is AgendaItem.TaskUi -> agendaItem.time.toFormatAgendaUiDate()
            },
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                fontFamily = inter,
                color = when (agendaItem) {
                    is AgendaItem.EventUi -> TaskyDarkGrey
                    is AgendaItem.ReminderUi -> TaskyDarkGrey
                    is AgendaItem.TaskUi -> TaskyWhite
                },
                textAlign = TextAlign.Right
            ),
            modifier = Modifier
                .fillMaxWidth()
        )

    }

}

@Composable
private fun AgendaItemContextMenu(
    expanded: Boolean,
    onClose: () -> Unit,
    onEdit: () -> Unit,
    onView: () -> Unit,
    onDelete: () -> Unit
) {

    TaskyDropDownMenu(
        expanded = expanded,
        onClose = onClose
    ) {

        TaskyDropDownMenuItem(
            label = stringResource(id = R.string.open),
            onClick = onView
        )

        HorizontalDivider(
            color = TaskyLight
        )
        TaskyDropDownMenuItem(
            label = stringResource(id = R.string.edit),
            onClick = onEdit
        )
        HorizontalDivider(
            color = TaskyLight
        )
        TaskyDropDownMenuItem(
            label = stringResource(id = R.string.delete),
            onClick = onDelete
        )

    }

}


@Preview(
    showBackground = true
)
@Composable
private fun BasicAgendaItemPreview() {
    TaskyTheme {
        AgendaItemUi(
            agendaItem = FakeEventUi,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onDelete = {},
            onEdit = {},
            onToggle = {},
            onView = {},
            isContextMenuOpen = false,
            onToggleContextMenu = {

            }
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
            agendaItem = FakeTaskUi,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onDelete = {},
            onEdit = {},
            onToggle = {},
            onView = {},
            isContextMenuOpen = false,
            onToggleContextMenu = {

            }
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun BasicAgendaItemTaskUiDonePreview() {
    TaskyTheme {
        AgendaItemUi(
            agendaItem = FakeTaskUi.copy(
                isDone = true
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onDelete = {},
            onEdit = {},
            onToggle = {},
            onView = {},
            isContextMenuOpen = false,
            onToggleContextMenu = {

            }
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
            agendaItem = FakeRemainderUi,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onDelete = {},
            onEdit = {},
            onToggle = {},
            onView = {},
            isContextMenuOpen = false,
            onToggleContextMenu = {

            }
        )
    }
}