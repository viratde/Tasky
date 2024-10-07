package com.tasky.core.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasky.core.presentation.designsystem.R
import com.tasky.core.presentation.designsystem.ui.TaskyBlack
import com.tasky.core.presentation.designsystem.ui.TaskyTheme
import com.tasky.core.presentation.designsystem.ui.TaskyWhite
import com.tasky.core.presentation.designsystem.ui.inter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskyDatePicker(
    modifier: Modifier = Modifier,
    minUtcTimeMillis: Long? = null,
    maxUtcTimeMillis: Long? = null,
    selectedDateUtcTimeMillis: Long? = null,
    onSelectionChange: (selectedDateTimeUtcMillis: Long?) -> Unit
) {

    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return (maxUtcTimeMillis == null || maxUtcTimeMillis >= utcTimeMillis) && (minUtcTimeMillis == null || minUtcTimeMillis <= utcTimeMillis)
            }
        },
        initialSelectedDateMillis = selectedDateUtcTimeMillis,
        initialDisplayedMonthMillis = selectedDateUtcTimeMillis
    )


    LaunchedEffect(key1 = selectedDateUtcTimeMillis) {
        if (datePickerState.selectedDateMillis != selectedDateUtcTimeMillis) {
            datePickerState.selectedDateMillis = selectedDateUtcTimeMillis
            if (selectedDateUtcTimeMillis != null) {
                datePickerState.displayedMonthMillis = selectedDateUtcTimeMillis
            }
        }
    }


    Column(
        modifier = modifier
            .background(TaskyWhite)
            .padding(top = 16.dp)
    ) {
        DatePicker(
            state = datePickerState,
            headline = null,
            title = null,
            showModeToggle = false,
            colors = DatePickerDefaults.colors(
                containerColor = TaskyWhite,
                weekdayContentColor = MaterialTheme.colorScheme.primary,
                subheadContentColor = MaterialTheme.colorScheme.primary,
                navigationContentColor = MaterialTheme.colorScheme.primary,
                currentYearContentColor = MaterialTheme.colorScheme.primary,

                selectedDayContainerColor = MaterialTheme.colorScheme.primary,
                selectedDayContentColor = MaterialTheme.colorScheme.background,
                todayDateBorderColor = MaterialTheme.colorScheme.primary,
                todayContentColor = MaterialTheme.colorScheme.primary,
                selectedYearContentColor = MaterialTheme.colorScheme.background,
                selectedYearContainerColor = MaterialTheme.colorScheme.primary,
                dividerColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Button(
                onClick = {
                    onSelectionChange(datePickerState.selectedDateMillis)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = TaskyBlack,
                    contentColor = TaskyWhite
                )
            ) {
                Text(
                    text = stringResource(id = R.string.apply),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = TaskyWhite,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = inter,
                        fontSize = 15.sp
                    )
                )
            }

        }
    }

}

@Preview
@Composable
private fun TaskyDatePickerTextFieldPreview() {
    TaskyTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            TaskyDatePicker {

            }
        }
    }
}