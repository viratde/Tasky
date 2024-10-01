package com.tasky.agenda.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tasky.core.presentation.designsystem.components.TaskyScaffold

@Composable
fun AgendaScreen() {

    TaskyScaffold {

        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {


            Text(
                text = "Agenda"
            )

        }

    }
}