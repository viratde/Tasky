package com.tasky.core.presentation.designsystem.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasky.core.presentation.designsystem.ui.TaskyBlack
import com.tasky.core.presentation.designsystem.ui.TaskyLight
import com.tasky.core.presentation.designsystem.ui.TaskyTheme
import com.tasky.core.presentation.designsystem.ui.inter

@Composable
fun TaskyButton(
    modifier: Modifier = Modifier,
    label: String,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {

    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = PaddingValues(
            vertical = 16.dp,
            horizontal = 16.dp
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = TaskyBlack,
            contentColor = TaskyLight
        )
    ) {

        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = inter,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
        )

    }

}

@Preview(
    showBackground = true
)
@Composable
private fun TaskyButtonPreview() {
    TaskyTheme {
        TaskyButton(
            modifier = Modifier
                .fillMaxWidth(),
            label = "Get Started",
        )
    }
}