package com.tasky.agenda.presentation.event_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasky.core.presentation.designsystem.ui.TaskyDarkGrey
import com.tasky.core.presentation.designsystem.ui.inter

@Composable
fun TaskyTitle(
    modifier: Modifier = Modifier,
    title: String,
    color: Color
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(20.dp)
                .background(color)
        )

        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = TaskyDarkGrey,
                fontWeight = FontWeight.SemiBold,
                fontFamily = inter,
                fontSize = 16.sp
            )
        )
    }

}