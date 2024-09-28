package com.tasky.core.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tasky.core.presentation.designsystem.ui.TaskyBlack


@Composable
fun TaskyScaffold(
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit
) {

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(TaskyBlack)
            .statusBarsPadding()
            .navigationBarsPadding(),
        contentWindowInsets = WindowInsets(
            left = 0.dp,
            bottom = 0.dp,
            top = 0.dp,
            right = 0.dp
        )
    ) { innerPadding ->
        content(innerPadding)
    }

}