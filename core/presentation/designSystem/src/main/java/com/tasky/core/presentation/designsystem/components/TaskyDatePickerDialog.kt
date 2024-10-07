package com.tasky.core.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.tasky.core.presentation.designsystem.R
import com.tasky.core.presentation.designsystem.ui.TaskyBlack
import com.tasky.core.presentation.designsystem.ui.TaskyTheme
import com.tasky.core.presentation.designsystem.ui.TaskyWhite
import com.tasky.core.presentation.designsystem.ui.inter
import java.time.LocalDate

@Composable
fun TaskyDatePickerDialog(
    modifier: Modifier = Modifier,
    minUtcTimeMillis: Long? = null,
    maxUtcTimeMillis: Long? = null,
    selectedDateUtcTimeMillis: Long? = null,
    onSelectionChange: (selectedDateTimeUtcMillis: Long?) -> Unit,
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
            TaskyDatePicker(
                maxUtcTimeMillis = maxUtcTimeMillis,
                minUtcTimeMillis = minUtcTimeMillis,
                selectedDateUtcTimeMillis = selectedDateUtcTimeMillis,
                onSelectionChange = onSelectionChange,
                modifier = modifier
            )
        }
    }

}

@Preview
@Composable
private fun TaskyDatePickerDialogPreview() {
    TaskyTheme {

    }
}