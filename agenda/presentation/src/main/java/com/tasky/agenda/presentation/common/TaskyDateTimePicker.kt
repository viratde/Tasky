package com.tasky.agenda.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasky.core.presentation.designsystem.components.TaskyDatePickerDialog
import com.tasky.core.presentation.designsystem.components.TaskyTimePickerDialog
import com.tasky.core.presentation.designsystem.ui.RightArrowIcon
import com.tasky.core.presentation.designsystem.ui.TaskyBlack
import com.tasky.core.presentation.designsystem.ui.TaskyTheme
import com.tasky.core.presentation.designsystem.ui.inter
import com.tasky.core.presentation.ui.getHour
import com.tasky.core.presentation.ui.getMinute
import com.tasky.core.presentation.ui.toUiDate
import com.tasky.core.presentation.ui.toUiTime
import com.tasky.core.presentation.ui.withHourAndMinutes
import java.time.LocalDateTime
import java.time.ZoneOffset

@Composable
fun TaskyDateTimePicker(
    modifier: Modifier = Modifier,
    title: String,
    dateTime: Long,
    onChange: (Long) -> Unit
) {


    var isDatePickerOpen by remember {
        mutableStateOf(false)
    }

    var isTimePickerOpen by remember {
        mutableStateOf(false)
    }


    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {


        Row(
            modifier = Modifier
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TaskyBlack,
                    fontFamily = inter,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                ),
                modifier = Modifier
                    .width(50.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))


            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        isTimePickerOpen = !isTimePickerOpen
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = dateTime.toUiTime(),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = TaskyBlack,
                        fontFamily = inter,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                    ),
                )

                IconButton(onClick = { isTimePickerOpen = !isTimePickerOpen }) {
                    Icon(
                        imageVector = RightArrowIcon,
                        contentDescription = null,
                        tint = TaskyBlack
                    )
                }
            }

        }


        Spacer(modifier = Modifier.width(32.dp))

        Row(
            modifier = Modifier
                .weight(1f)
                .clickable {
                    isDatePickerOpen = !isDatePickerOpen
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = dateTime.toUiDate(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TaskyBlack,
                    fontFamily = inter,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                )
            )

            IconButton(onClick = { isDatePickerOpen = !isDatePickerOpen }) {
                Icon(
                    imageVector = RightArrowIcon,
                    contentDescription = null,
                    tint = TaskyBlack
                )
            }
        }

    }

    if (isDatePickerOpen) {
        TaskyDatePickerDialog(
            selectedDateUtcTimeMillis = dateTime,
            onSelectionChange = { date ->
                date?.let {
                    onChange(it)
                    isDatePickerOpen = false
                }
            },
            onClose = {
                isDatePickerOpen = false
            }
        )
    }

    if (isTimePickerOpen) {
        TaskyTimePickerDialog(
            hour = dateTime.getHour(),
            minutes = dateTime.getMinute(),
            is24Hour = false,
            onChange = { hour: Int, minute: Int ->
                onChange(
                    dateTime.withHourAndMinutes(hour, minute)
                )
                isTimePickerOpen = false
            },
            onClose = {
                isTimePickerOpen = false
            }
        )
    }


}

@Preview(
    showBackground = true
)
@Composable
private fun TaskyDateTimePickerPreview() {
    TaskyTheme {
        TaskyDateTimePicker(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            title = "From",
            dateTime = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000L
        ) {

        }
    }
}