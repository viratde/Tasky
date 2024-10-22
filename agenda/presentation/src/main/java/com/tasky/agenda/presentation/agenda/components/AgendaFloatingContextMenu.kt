package com.tasky.agenda.presentation.agenda.components

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.tasky.agenda.presentation.R
import com.tasky.core.presentation.designsystem.components.TaskyDropDownMenu
import com.tasky.core.presentation.designsystem.components.TaskyDropDownMenuItem
import com.tasky.core.presentation.designsystem.ui.TaskyLight

@Composable
fun AgendaFloatingContextMenu(
    expanded: Boolean,
    onClose: () -> Unit,
    onAddEvent: () -> Unit,
    onAddTask: () -> Unit,
    onAddReminder: () -> Unit,
    modifier: Modifier = Modifier
) {

    TaskyDropDownMenu(
        expanded = expanded,
        onClose = onClose,
        modifier = modifier
    ) {

        TaskyDropDownMenuItem(
            label = stringResource(id = R.string.event),
            onClick = onAddEvent
        )

        HorizontalDivider(
            color = TaskyLight
        )

        TaskyDropDownMenuItem(
            label = stringResource(id = R.string.task),
            onClick = onAddTask
        )

        HorizontalDivider(
            color = TaskyLight
        )

        TaskyDropDownMenuItem(
            label = stringResource(id = R.string.remainder),
            onClick = onAddReminder
        )

    }

}