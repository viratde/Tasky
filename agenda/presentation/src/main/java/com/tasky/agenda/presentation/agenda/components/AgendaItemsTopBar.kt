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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasky.core.presentation.designsystem.ui.TaskyLight
import com.tasky.core.presentation.designsystem.ui.TaskyLightBlue
import com.tasky.core.presentation.designsystem.ui.TaskyTheme
import com.tasky.core.presentation.designsystem.ui.TaskyWhite
import com.tasky.core.presentation.designsystem.ui.inter
import com.tasky.core.presentation.ui.formattedUiName
import java.time.LocalDate

@Composable
fun AgendaItemsTopBar(
    selectedDate: LocalDate,
    onToggleDateSelector: () -> Unit,
    name: String,
    onToggleLogoutDropDown: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Text(
                text = selectedDate.month.name,
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
                text = name.formattedUiName(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TaskyLightBlue,
                    fontFamily = inter,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )

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
            selectedDate = LocalDate.now(),
            onToggleDateSelector = {

            },
            name = "Virat Kumar",
            onToggleLogoutDropDown = {

            }
        )
    }
}