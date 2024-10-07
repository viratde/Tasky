package com.tasky.core.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties


@Composable
fun TaskyTimePickerDialog(
    modifier: Modifier = Modifier,
    hour: Int,
    minutes: Int,
    is24Hour: Boolean,
    onChange: (hour: Int, minute: Int) -> Unit,
    onClose: () -> Unit
) {

    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.1f
                    )
                ),
            verticalArrangement = Arrangement.Center
        ) {

            TaskyTimePicker(
                modifier = modifier,
                hour = hour,
                minutes = minutes,
                is24Hour = is24Hour,
                onChange = onChange
            )
        }
    }


}
