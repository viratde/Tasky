package com.tasky.core.presentation.designsystem.components


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tasky.core.presentation.designsystem.ui.CheckedIcon
import com.tasky.core.presentation.designsystem.ui.TaskyGreen

@Composable
fun TaskySuccessIcon(
    isVisible: Boolean = false,
) {
    if (isVisible) {
        Box(
            modifier = Modifier.size(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = CheckedIcon,
                contentDescription = null,
                tint = TaskyGreen
            )
        }
    }
}