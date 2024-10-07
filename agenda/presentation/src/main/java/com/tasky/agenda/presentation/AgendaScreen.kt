package com.tasky.agenda.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tasky.agenda.presentation.common.TaskyDateTimePicker
import com.tasky.core.presentation.designsystem.components.TaskyScaffold
import com.tasky.core.presentation.designsystem.ui.TaskyWhite
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime

@Composable
fun AgendaScreen() {
    TaskyScaffold {
        Box(
            modifier =
            Modifier
                .padding(it)
                .background(TaskyWhite)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {


            var date by remember {
                mutableLongStateOf(ZonedDateTime.now().toInstant().toEpochMilli())
            }


            TaskyDateTimePicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                title = "From",
                dateTime = date
            ) {
                date = it
            }


        }
    }
}
