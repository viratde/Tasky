package com.tasky.agenda.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasky.core.presentation.designsystem.ui.TaskyBlack
import com.tasky.core.presentation.designsystem.ui.TaskyTheme
import com.tasky.core.presentation.designsystem.ui.TaskyWhite
import com.tasky.core.presentation.designsystem.ui.inter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskyDescriptionTextField(
    modifier: Modifier = Modifier,
    placeHolder: String,
    title: String,
    text: String,
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
        Text(
            text = text.ifEmpty { placeHolder },
            style = MaterialTheme.typography.bodyMedium.copy(
                color = TaskyBlack,
                fontFamily = inter,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier
                .weight(1f)
        )

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
                isTitle = false,
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
private fun TaskyDescriptionTextFieldPreview() {
    TaskyTheme {
        TaskyDescriptionTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeHolder = "Task Title",
            title = "TASK TITLE",
            text = ""
        ) {

        }
    }
}