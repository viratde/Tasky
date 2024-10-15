package com.tasky.core.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tasky.core.presentation.designsystem.ui.TaskyBlack


@Composable
fun TaskyScaffold(
    modifier: Modifier = Modifier,
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit = {}
) {
    Scaffold(
        modifier =
        modifier
            .fillMaxSize()
            .background(TaskyBlack),
        containerColor = TaskyBlack,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = FabPosition.EndOverlay
    ) { innerPadding ->
        content(innerPadding)
    }
}
