package com.tasky.core.presentation.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasky.core.presentation.designsystem.ui.TaskyBlack
import com.tasky.core.presentation.designsystem.ui.TaskyDarkGrey
import com.tasky.core.presentation.designsystem.ui.TaskyGreen
import com.tasky.core.presentation.designsystem.ui.TaskyGrey
import com.tasky.core.presentation.designsystem.ui.TaskyLight
import com.tasky.core.presentation.designsystem.ui.TaskyTheme
import com.tasky.core.presentation.designsystem.ui.TaskyWhite
import com.tasky.core.presentation.designsystem.ui.inter

@Composable
fun TaskyButton(
    label: String,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {

    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = TaskyBlack,
            contentColor = TaskyLight,
            disabledContainerColor = TaskyGrey,
            disabledContentColor = TaskyWhite
        )
    ) {

        Box(
            modifier = Modifier
                .height(IntrinsicSize.Min),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = inter,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                ),
                modifier = Modifier.alpha(if (isLoading) 0f else 1f)
            )

            CircularProgressIndicator(
                color = TaskyWhite,
                modifier = Modifier
                    .alpha(if (isLoading) 1f else 0f)
            )

        }

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

@Preview(
    showBackground = true
)
@Composable
private fun TaskyDisabledButtonPreview() {
    TaskyTheme {
        TaskyButton(
            modifier = Modifier
                .fillMaxWidth(),
            enabled = false,
            label = "Get Started",
        )
    }
}
