package com.tasky.agenda.presentation.agenda_item_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasky.agenda.presentation.R
import com.tasky.agenda.presentation.agenda_item_details.components.utils.InputType
import com.tasky.core.presentation.designsystem.components.TaskyScaffold
import com.tasky.core.presentation.designsystem.ui.LeftArrowIcon
import com.tasky.core.presentation.designsystem.ui.TaskyBlack
import com.tasky.core.presentation.designsystem.ui.TaskyGreen
import com.tasky.core.presentation.designsystem.ui.TaskyTheme
import com.tasky.core.presentation.designsystem.ui.TaskyWhite
import com.tasky.core.presentation.designsystem.ui.inter



@Composable
fun TaskyFullScreenTextField(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    inputType: InputType,
    onSave: (String) -> Unit,
    onBack: () -> Unit
) {

    var text by remember(value) {
        mutableStateOf(value)
    }

    TaskyScaffold { innerPadding ->

        Column(
            modifier = modifier
                .padding(innerPadding)
                .background(TaskyWhite)
                .fillMaxWidth()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 6.dp,
                        vertical = 6.dp
                    ),
                contentAlignment = Alignment.Center
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    IconButton(
                        onClick = onBack
                    ) {

                        Icon(
                            imageVector = LeftArrowIcon,
                            contentDescription = null,
                            tint = TaskyBlack
                        )

                    }



                    TextButton(
                        onClick = { onSave(text) },
                        modifier = Modifier
                    ) {
                        Text(
                            text = stringResource(id = R.string.save),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TaskyGreen,
                                fontFamily = inter,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }

                }
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = TaskyBlack,
                        fontFamily = inter,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }

            HorizontalDivider()

            TextField(
                value = text,
                onValueChange = {
                    text = it
                },
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = inter,
                    fontSize = if (inputType == InputType.TITLE) 26.sp else 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = TaskyBlack
                ),
                singleLine = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = TaskyWhite,
                    focusedContainerColor = TaskyWhite,
                    unfocusedTextColor = TaskyBlack,
                    focusedTextColor = TaskyBlack
                )
            )

        }

    }


}


@Preview
@Composable
private fun TaskyFullScreenTextFieldPreview() {
    TaskyTheme {
        TaskyFullScreenTextField(
            title = "EDIT TITLE",
            onSave = {

            },
            inputType = InputType.TITLE,
            value = "Meeting",
            onBack = {

            }
        )
    }
}

@Preview
@Composable
private fun TaskyFullScreenTextFieldDescPreview() {
    TaskyTheme {
        TaskyFullScreenTextField(
            title = "EDIT TITLE",
            onSave = {

            },
            inputType = InputType.DESCRIPTION,
            value = "Meeting",
            onBack = {

            }
        )
    }
}