package com.tasky.agenda.presentation.agenda.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.tasky.agenda.presentation.R
import com.tasky.core.presentation.designsystem.components.TaskyDropDownMenuItem
import com.tasky.core.presentation.designsystem.components.TaskyDatePickerDialog
import com.tasky.core.presentation.designsystem.components.TaskyDropDownMenu
import com.tasky.core.presentation.designsystem.ui.TaskyLight
import com.tasky.core.presentation.designsystem.ui.TaskyLightBlue
import com.tasky.core.presentation.designsystem.ui.TaskyTheme
import com.tasky.core.presentation.designsystem.ui.TaskyWhite
import com.tasky.core.presentation.designsystem.ui.inter
import com.tasky.core.presentation.ui.formattedUiName
import com.tasky.core.presentation.ui.toMonthName
import java.time.ZonedDateTime

@Composable
fun AgendaItemsTopBar(
    selectedDate: Long,
    isDateSelectorModelOpen: Boolean,
    onToggleDateSelector: () -> Unit,
    onSelectedDateChange: (Long) -> Unit,
    name: String?,
    isLogOutDropDownOpen: Boolean,
    onToggleLogoutDropDown: () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
) {

    if (isDateSelectorModelOpen) {

        TaskyDatePickerDialog(
            selectedDateUtcTimeMillis = selectedDate,
            onSelectionChange = { date ->
                onSelectedDateChange(date)
            },
            onClose = {
                onToggleDateSelector()
            }
        )

    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                    onToggleDateSelector()
                }
        ) {

            Text(
                text = selectedDate.toMonthName(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TaskyWhite,
                    fontFamily = inter,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            IconButton(onClick = onToggleDateSelector) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = TaskyWhite
                )
            }

        }


        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(TaskyLight)
                .clickable {
                    onToggleLogoutDropDown()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name?.formattedUiName() ?: "",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TaskyLightBlue,
                    fontFamily = inter,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
            TaskyDropDownMenu(
                expanded = isLogOutDropDownOpen,
                onClose = { onToggleLogoutDropDown() },
            ) {
                TaskyDropDownMenuItem(
                    label = stringResource(id = R.string.logout),
                    onClick = onLogout
                )
            }
        }

    }

}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
private fun AgendaItemsTopBarPreview() {
    TaskyTheme {
        AgendaItemsTopBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            selectedDate = ZonedDateTime.now().toInstant().toEpochMilli(),
            onToggleDateSelector = {

            },
            name = "Virat Kumar",
            isDateSelectorModelOpen = false,
            onToggleLogoutDropDown = {

            },
            onSelectedDateChange = {

            },
            onLogout = {

            },
            isLogOutDropDownOpen = false
        )
    }
}