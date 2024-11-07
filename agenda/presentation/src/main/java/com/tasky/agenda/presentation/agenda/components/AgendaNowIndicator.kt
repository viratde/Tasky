package com.tasky.agenda.presentation.agenda.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tasky.core.presentation.designsystem.ui.TaskyBlack
import com.tasky.core.presentation.designsystem.ui.TaskyTheme

@Composable
fun AgendaNowIndicator(
    modifier: Modifier = Modifier
) {

    Box (
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){

        HorizontalDivider(
            color = TaskyBlack,
            thickness = 2.dp,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
        )

        Box (
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(TaskyBlack, CircleShape)
                .align(Alignment.CenterStart)
        )

    }

}

@Preview(
    showBackground = true
)
@Composable
private fun AgendaNowIndicatorPreview() {
    TaskyTheme {
        AgendaNowIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}