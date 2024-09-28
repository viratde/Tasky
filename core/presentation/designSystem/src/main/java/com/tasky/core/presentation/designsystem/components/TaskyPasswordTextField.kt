package com.tasky.core.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasky.core.presentation.designsystem.R
import com.tasky.core.presentation.designsystem.ui.EyeOffIcon
import com.tasky.core.presentation.designsystem.ui.EyeOnIcon
import com.tasky.core.presentation.designsystem.ui.TaskyDarkGrey
import com.tasky.core.presentation.designsystem.ui.TaskyError
import com.tasky.core.presentation.designsystem.ui.TaskyGrey
import com.tasky.core.presentation.designsystem.ui.TaskyLight2
import com.tasky.core.presentation.designsystem.ui.TaskyTextFieldPlaceHolderColor
import com.tasky.core.presentation.designsystem.ui.TaskyTheme
import com.tasky.core.presentation.designsystem.ui.inter

@Composable
fun TaskyPasswordTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeHolder: String,
    error: Boolean = false,
    enabled: Boolean = true,
    isVisible: Boolean = false,
    onToggleVisibility: () -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions()
) {

    val context = LocalContext.current

    val textStyle = MaterialTheme.typography.bodyMedium.copy(
        fontFamily = inter,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )


    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        enabled = enabled,
        textStyle = textStyle.copy(
            color = TaskyDarkGrey
        ),
        maxLines = 1,
        modifier = Modifier,
        decorationBox = {

            Row(
                modifier = modifier
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        1.dp,
                        if (error) TaskyError else Color.Transparent,
                        RoundedCornerShape(10.dp)
                    )
                    .background(TaskyLight2)
                    .padding(
                        vertical = 24.dp - 12.dp,
                        horizontal = 16.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(IntrinsicSize.Max)
                ) {

                    Text(
                        text = if (value.isEmpty()) placeHolder else "",
                        style = textStyle.copy(
                            color = TaskyTextFieldPlaceHolderColor
                        )
                    )

                    it()

                }

                Spacer(modifier = Modifier.width(8.dp))


                Icon(
                    imageVector = if (isVisible) EyeOnIcon else EyeOffIcon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(
                            48.dp
                        )
                        .clip(CircleShape)
                        .clickable {
                            onToggleVisibility()
                        }
                        .semantics {
                            contentDescription = context.getString(R.string.password_visiblity)
                            stateDescription =
                                context.getString(if (isVisible) R.string.on else R.string.off)
                            onClick(
                                context.getString(R.string.click_to_toggle)
                            ) {
                                onToggleVisibility()
                                true
                            }
                        }
                        .padding(12.dp),
                    tint = TaskyGrey
                )

            }
        }
    )

}


@Preview(
    showBackground = true
)
@Composable
private fun TaskyTextFieldPreview() {
    TaskyTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            TaskyPasswordTextField(
                value = "",
                onValueChange = {},
                placeHolder = "Password",
            )

            TaskyPasswordTextField(
                value = "",
                onValueChange = {},
                isVisible = true,
                placeHolder = "Password",
            )

        }
    }
}