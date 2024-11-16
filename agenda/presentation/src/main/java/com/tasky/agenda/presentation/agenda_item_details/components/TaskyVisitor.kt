package com.tasky.agenda.presentation.agenda_item_details.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasky.agenda.presentation.R
import com.tasky.core.presentation.designsystem.ui.DeleteIcon
import com.tasky.core.presentation.designsystem.ui.TaskyDarkGrey
import com.tasky.core.presentation.designsystem.ui.TaskyGrey
import com.tasky.core.presentation.designsystem.ui.TaskyLight2
import com.tasky.core.presentation.designsystem.ui.TaskyLightBlue
import com.tasky.core.presentation.designsystem.ui.TaskyTheme
import com.tasky.core.presentation.designsystem.ui.TaskyWhite
import com.tasky.core.presentation.designsystem.ui.inter
import com.tasky.core.presentation.ui.formattedUiName

@Composable
fun TaskyVisitor(
    modifier: Modifier = Modifier,
    name: String,
    isCreator: Boolean,
    isEnabled:Boolean,
    onDelete: () -> Unit,
) {

    val opacity by animateFloatAsState(targetValue = if (isCreator) 1f else 0f, label = "")

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .background(TaskyLight2)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(32.dp)
                .background(TaskyGrey),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name.formattedUiName(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TaskyWhite,
                    fontFamily = inter,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                )
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = TaskyDarkGrey,
                fontFamily = inter,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            ),
            modifier = Modifier.weight(1f)
        )

        Box(
            modifier = Modifier
                .height(IntrinsicSize.Max),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.creator),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TaskyLightBlue,
                    fontFamily = inter,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                ),
                modifier = Modifier.graphicsLayer {
                    alpha = opacity
                }
            )

            IconButton(
                onClick = onDelete,
                enabled = isEnabled
            ) {
                Icon(
                    imageVector = DeleteIcon,
                    contentDescription = null,
                    tint = TaskyDarkGrey,
                    modifier = Modifier
                        .graphicsLayer {
                            alpha = 1f - opacity
                        }
                )
            }
        }

    }

}

@Preview
@Composable
private fun TaskyVisitorCreatorPreview() {
    TaskyTheme {
        TaskyVisitor(
            name = "Virat Kumar",
            isCreator = false,
            isEnabled = false,
            onDelete = {

            }
        )
    }
}

@Preview
@Composable
private fun TaskyVisitorPreview() {
    TaskyTheme {
        TaskyVisitor(
            name = "Virat Kumar",
            isCreator = true,
            isEnabled = true,
            onDelete = {

            }
        )
    }
}

@Preview
@Composable
private fun TaskyVisitorDisabledPreview() {
    TaskyTheme {
        TaskyVisitor(
            name = "Virat Kumar",
            isCreator = false,
            isEnabled = false,
            onDelete = {

            }
        )
    }
}