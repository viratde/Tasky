package com.tasky.agenda.presentation.agenda.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasky.core.presentation.designsystem.ui.TaskyDarkGrey
import com.tasky.core.presentation.designsystem.ui.TaskyGrey
import com.tasky.core.presentation.designsystem.ui.TaskyOrange
import com.tasky.core.presentation.designsystem.ui.TaskyTheme
import com.tasky.core.presentation.designsystem.ui.inter
import java.time.LocalDate

@Composable
fun AgendaDateRange(
    selectionStartDate: LocalDate,
    selectedDate: LocalDate,
    noOfDaysToRender: Int,
    onSelectionDateChanged: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {

    val dates = remember(selectionStartDate, noOfDaysToRender) {
        List(noOfDaysToRender) { index ->
            selectionStartDate.plusDays(index.toLong())
        }
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        dates.map { date ->
            AgendaDate(
                date = date,
                selected = selectedDate == date,
                onSelect = {
                    onSelectionDateChanged(date)
                }
            )
        }
    }

}

@Composable
private fun AgendaDate(
    date: LocalDate,
    selected: Boolean,
    onSelect: () -> Unit
) {

    Column(
        modifier = Modifier
            .width(40.dp)
            .clip(
                RoundedCornerShape(
                    30.dp
                )
            )
            .background(
                if (selected) TaskyOrange else Color.Transparent
            )
            .clickable(!selected) {
                onSelect()
            }
            .padding(vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = date.dayOfWeek.name.first().toString().uppercase(),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = if (selected) TaskyDarkGrey else TaskyGrey,
                fontFamily = inter,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = date.dayOfMonth.toString(),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = if (selected) TaskyDarkGrey else TaskyGrey,
                fontFamily = inter,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }

}


@Preview(
    showBackground = true
)
@Composable
private fun AgendaDateRangePreview() {
    TaskyTheme {
        AgendaDateRange(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            noOfDaysToRender = 6,
            selectionStartDate = LocalDate.now(),
            selectedDate = LocalDate.now().plusDays(1L),
            onSelectionDateChanged = {

            }
        )
    }
}