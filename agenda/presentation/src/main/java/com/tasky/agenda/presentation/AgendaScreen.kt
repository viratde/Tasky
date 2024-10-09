package com.tasky.agenda.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tasky.agenda.presentation.common.InputType
import com.tasky.agenda.presentation.common.TaskyTextField
import com.tasky.core.presentation.designsystem.components.TaskyScaffold
import com.tasky.core.presentation.designsystem.ui.TaskyWhite

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
                mutableStateOf("")
            }


            TaskyTextField(
                placeHolder = "Task Title",
                title = "TASK TITLE",
                text = date,
                inputType = InputType.TITLE
            ) {
                date = it
            }

        }
    }
}
