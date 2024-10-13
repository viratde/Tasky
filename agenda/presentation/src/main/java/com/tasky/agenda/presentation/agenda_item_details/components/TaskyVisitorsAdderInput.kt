package com.tasky.agenda.presentation.agenda_item_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.tasky.agenda.presentation.R
import com.tasky.core.presentation.designsystem.components.TaskyButton
import com.tasky.core.presentation.designsystem.components.TaskyTextField
import com.tasky.core.presentation.designsystem.ui.CheckedIcon
import com.tasky.core.presentation.designsystem.ui.TaskyBlack
import com.tasky.core.presentation.designsystem.ui.TaskyGreen
import com.tasky.core.presentation.designsystem.ui.TaskyTheme
import com.tasky.core.presentation.designsystem.ui.TaskyWhite
import com.tasky.core.presentation.designsystem.ui.inter

@Composable
fun TaskyVisitorsAdderInput(
    email: String,
    onClose: () -> Unit,
    isValidEmail: Boolean,
    isLoading: Boolean,
    onAdd: () -> Unit,
    onEmailChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
    ) {

        Column(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(5.dp))
                .background(TaskyWhite)
                .padding(
                    vertical = 16.dp
                )
        ) {

            Text(
                text = stringResource(id = R.string.add_visitor),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TaskyBlack,
                    fontFamily = inter,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 26.sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TaskyTextField(
                value = email,
                placeHolder = stringResource(id = R.string.attendee_email),
                onValueChange = onEmailChange,
                error = !isValidEmail,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp
                    ),
                endIcon = {
                    if (isValidEmail) {
                        Icon(
                            imageVector = CheckedIcon,
                            contentDescription = null,
                            tint = TaskyGreen
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onAdd()
                    }
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            TaskyButton(
                label = stringResource(id = R.string.add),
                onClick = onAdd,
                enabled = isValidEmail,
                isLoading = isLoading,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )

        }


    }

}

@Preview
@Composable
private fun TaskyVisitorsInputPreview() {
    TaskyTheme {
        TaskyVisitorsAdderInput(
            modifier = Modifier
                .fillMaxWidth(),
            email = "",
            onClose = { /*TODO*/ },
            isValidEmail = true,
            isLoading = true,
            onAdd = {},
            onEmailChange = {

            }
        )
    }
}