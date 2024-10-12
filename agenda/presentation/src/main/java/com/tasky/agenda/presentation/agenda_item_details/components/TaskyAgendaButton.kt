package com.tasky.agenda.presentation.agenda_item_details.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasky.core.presentation.designsystem.ui.TaskyGrey
import com.tasky.core.presentation.designsystem.ui.TaskyTheme
import com.tasky.core.presentation.designsystem.ui.inter

@Composable
fun TaskyAgendaButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier
            .clickable(enabled) {
                onClick()
            },
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = TaskyGrey,
                fontFamily = inter,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        )
    }

}


@Preview(
    showBackground = true
)
@Composable
private fun TaskyLeaveEventOrDeleteAgendaItemPreview() {
    TaskyTheme {
        TaskyAgendaButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            enabled = true,
            text = "Leave Event",
            onClick = {

            }
        )
    }
}