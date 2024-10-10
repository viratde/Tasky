package com.tasky.agenda.presentation.event_details.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasky.agenda.presentation.event_details.components.utils.RemindTimes
import com.tasky.agenda.presentation.event_details.components.utils.asUiText
import com.tasky.core.presentation.designsystem.ui.NotificationIcon
import com.tasky.core.presentation.designsystem.ui.RightArrowIcon
import com.tasky.core.presentation.designsystem.ui.TaskyBlack
import com.tasky.core.presentation.designsystem.ui.TaskyGrey
import com.tasky.core.presentation.designsystem.ui.TaskyLight2
import com.tasky.core.presentation.designsystem.ui.TaskyTheme
import com.tasky.core.presentation.designsystem.ui.TaskyWhite
import com.tasky.core.presentation.designsystem.ui.inter

@Composable
fun TaskyRemindTimeInput(
    modifier: Modifier = Modifier,
    remindTime: RemindTimes,
    isEnabled: Boolean,
    onRemindTimeChange: (RemindTimes) -> Unit
) {

    var isDropDownMenuOpen by remember {
        mutableStateOf(false)
    }

    val opacity by animateFloatAsState(targetValue = if (isEnabled) 1f else 0f, label = "")

    Row(
        modifier = modifier
            .then(
                if (isEnabled) {
                    Modifier
                        .clickable {
                            isDropDownMenuOpen = !isDropDownMenuOpen
                        }
                } else {
                    Modifier
                }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = NotificationIcon,
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(TaskyLight2)
                .padding(
                    vertical = 4.dp,
                    horizontal = 6.dp
                ),
            tint = TaskyGrey
        )

        Spacer(modifier = Modifier.width(12.dp))

        Box(
            modifier = Modifier
                .weight(1f)
        ) {

            Text(
                text = remindTime.asUiText().asText(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TaskyBlack,
                    fontSize = 16.sp,
                    fontFamily = inter,
                    fontWeight = FontWeight.Normal
                )
            )

            DropdownMenu(
                expanded = isDropDownMenuOpen && isEnabled,
                onDismissRequest = {
                    isDropDownMenuOpen = false
                },
                containerColor = TaskyWhite,
                modifier = Modifier
            ) {

                RemindTimes.entries.map { time ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = time.asUiText().asText(),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = TaskyBlack,
                                    fontSize = 16.sp,
                                    fontFamily = inter,
                                    fontWeight = FontWeight.Normal
                                )
                            )
                        },
                        onClick = {
                            onRemindTimeChange(time)
                            isDropDownMenuOpen = false
                        }
                    )
                }
            }

        }

        IconButton(
            onClick = { isDropDownMenuOpen = !isDropDownMenuOpen },
            enabled = isEnabled,
            modifier = Modifier
                .alpha(opacity)
        ) {
            Icon(
                imageVector = RightArrowIcon,
                contentDescription = null,
                tint = TaskyBlack
            )
        }

    }


}


@Preview(
    showBackground = true
)
@Composable
private fun TaskyRemindTimeInputPreview() {
    TaskyTheme {
        TaskyRemindTimeInput(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            isEnabled = true,
            remindTime = RemindTimes.ONE_DAY
        ) {

        }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun TaskyDisabledRemindTimeInputPreview() {
    TaskyTheme {
        TaskyRemindTimeInput(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            isEnabled = false,
            remindTime = RemindTimes.ONE_DAY
        ) {

        }
    }
}