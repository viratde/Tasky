package com.tasky.agenda.presentation.common

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasky.core.presentation.designsystem.ui.RightArrowIcon
import com.tasky.core.presentation.designsystem.ui.TaskyBlack
import com.tasky.core.presentation.designsystem.ui.TaskyTheme
import com.tasky.core.presentation.designsystem.ui.TaskyWhite
import com.tasky.core.presentation.designsystem.ui.inter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskyTextField(
    modifier: Modifier = Modifier,
    placeHolder: String,
    title: String,
    text: String,
    isTitle: Boolean,
    onValueChange: (String) -> Unit
) {

    var isOpen by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = modifier
            .clickable {
                isOpen = !isOpen
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (isTitle) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .border(
                        2.dp,
                        TaskyBlack,
                        CircleShape
                    )
            )
        }
        Text(
            text = text.ifEmpty { placeHolder },
            style = MaterialTheme.typography.bodyMedium.copy(
                color = TaskyBlack,
                fontFamily = inter,
                fontSize = if (isTitle) 26.sp else 16.sp,
                fontWeight = if (isTitle) FontWeight.SemiBold else FontWeight.Normal
            ),
            modifier = Modifier
                .weight(1f)
        )

        if (isTitle) {
            Icon(
                imageVector = RightArrowIcon,
                contentDescription = null,
                tint = TaskyBlack
            )
        }

    }

    val bottomSheet = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    if (isOpen) {
        ModalBottomSheet(
            onDismissRequest = { isOpen = !isOpen },
            sheetState = bottomSheet,
            shape = RectangleShape,
            containerColor = TaskyWhite,
            contentColor = TaskyBlack,
            dragHandle = null
        ) {

            TaskyFullScreenTextField(
                modifier = Modifier
                    .fillMaxSize(),
                title = title,
                value = text,
                isTitle = isTitle,
                onSave = { value ->
                    onValueChange(value)
                    isOpen = !isOpen
                },
                onBack = {
                    isOpen = !isOpen
                }
            )
        }

    }


}

@Preview(
    showBackground = true
)
@Composable
private fun TaskyTitleTextFieldPreview() {
    TaskyTheme {
        TaskyTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeHolder = "Task Title",
            title = "TASK TITLE",
            isTitle = false,
            text = ""
        ) {

        }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun TaskyTitleTextFieldTitlePreview() {
    TaskyTheme {
        TaskyTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeHolder = "Task Title",
            title = "TASK TITLE",
            isTitle = true,
            text = ""
        ) {

        }
    }
}