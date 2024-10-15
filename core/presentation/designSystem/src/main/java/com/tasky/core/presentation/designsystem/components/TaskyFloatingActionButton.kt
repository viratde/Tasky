package com.tasky.core.presentation.designsystem.components

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import com.tasky.core.presentation.designsystem.ui.TaskyTheme

@Composable
fun TaskyFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = FloatingActionButtonDefaults.shape,
    containerColor: Color = FloatingActionButtonDefaults.containerColor,
    content: @Composable () -> Unit = {},
) {

    FloatingActionButton(
        onClick = onClick,
        shape = shape,
        containerColor = containerColor,
        modifier = modifier,
    ) {
        content()
    }

}

@Preview
@Composable
private fun TaskyFloatingActionButtonPreview() {
    TaskyTheme {

    }
}