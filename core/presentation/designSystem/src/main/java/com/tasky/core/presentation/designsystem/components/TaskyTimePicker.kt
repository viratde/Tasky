package com.tasky.core.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasky.core.presentation.designsystem.R
import com.tasky.core.presentation.designsystem.ui.TaskyBlack
import com.tasky.core.presentation.designsystem.ui.TaskyGrey
import com.tasky.core.presentation.designsystem.ui.TaskyTheme
import com.tasky.core.presentation.designsystem.ui.TaskyWhite
import com.tasky.core.presentation.designsystem.ui.inter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskyTimePicker(
    modifier: Modifier = Modifier,
    hour: Int,
    minutes: Int,
    is24Hour: Boolean,
    onChange: (hour: Int, minute: Int) -> Unit
) {

    val timePickerState = rememberTimePickerState(
        initialHour = hour,
        initialMinute = minutes,
        is24Hour = is24Hour
    )

    LaunchedEffect(hour, minutes) {
        if (hour != timePickerState.hour || timePickerState.minute != minutes) {
            timePickerState.hour = hour
            timePickerState.minute = minutes
        }
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(TaskyWhite)
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TimePicker(
            state = timePickerState,
            modifier = modifier,
            layoutType = TimePickerLayoutType.Vertical,
            colors = TimePickerDefaults.colors(
                clockDialColor = TaskyGrey,
                clockDialSelectedContentColor = TaskyWhite,
                clockDialUnselectedContentColor = TaskyBlack,
                containerColor = TaskyWhite,
                timeSelectorUnselectedContentColor = TaskyBlack,
                timeSelectorUnselectedContainerColor = TaskyGrey,
                timeSelectorSelectedContentColor = TaskyWhite,
                timeSelectorSelectedContainerColor = TaskyBlack,
                periodSelectorBorderColor = TaskyBlack,
                periodSelectorUnselectedContainerColor = TaskyGrey,
                periodSelectorUnselectedContentColor = TaskyWhite,
                periodSelectorSelectedContentColor = TaskyWhite,
                periodSelectorSelectedContainerColor = TaskyBlack
            )
        )
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Button(
                onClick = {
                    onChange(timePickerState.hour, timePickerState.minute)
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
private fun TimePickerPreview() {
    TaskyTheme {
        TaskyTimePicker(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            hour = 12,
            minutes = 57,
            is24Hour = false
        ) { hour, minutes ->

        }
    }
}