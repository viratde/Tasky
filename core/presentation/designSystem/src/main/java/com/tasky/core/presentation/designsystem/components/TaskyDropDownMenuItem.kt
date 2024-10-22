package com.tasky.core.presentation.designsystem.components

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.tasky.core.presentation.designsystem.ui.TaskyBlack
import com.tasky.core.presentation.designsystem.ui.TaskyTheme
import com.tasky.core.presentation.designsystem.ui.inter

@Composable
fun TaskyDropDownMenuItem(
    label: String,
    onClick: () -> Unit
) {

    DropdownMenuItem(
        text = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TaskyBlack,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = inter
                )
            )
        },
        onClick = onClick
    )

}

@Preview
@Composable
private fun TaskyDropDownMenuItemPreview() {
    TaskyTheme {
        TaskyDropDownMenuItem(label = "Logout") {

        }
    }
}