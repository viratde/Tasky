package com.tasky.agenda.presentation.event_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tasky.agenda.presentation.R
import com.tasky.core.presentation.designsystem.ui.CrossIcon
import com.tasky.core.presentation.designsystem.ui.EditIcon
import com.tasky.core.presentation.designsystem.ui.TaskyTheme
import com.tasky.core.presentation.designsystem.ui.TaskyWhite
import com.tasky.core.presentation.designsystem.ui.inter
import com.tasky.core.presentation.ui.toFullUiDate
import java.time.ZonedDateTime

@Composable
fun TaskyTopBar(
    modifier: Modifier = Modifier,
    date: Long,
    isInEditMode: Boolean,
    onCancel: () -> Unit,
    onEnableEditing: () -> Unit,
    onSave: () -> Unit
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            IconButton(onClick = onCancel) {
                Icon(
                    imageVector = CrossIcon,
                    contentDescription = null,
                    tint = TaskyWhite
                )
            }

            if (!isInEditMode) {
                IconButton(onClick = onEnableEditing) {
                    Icon(
                        imageVector = EditIcon,
                        contentDescription = null,
                        tint = TaskyWhite
                    )
                }
            } else {
                TextButton(onClick = onSave) {
                    Text(
                        text = stringResource(id = R.string.save),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = TaskyWhite,
                            fontFamily = inter,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                    )
                }
            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {


            Text(
                text = date.toFullUiDate(),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = TaskyWhite,
                    fontFamily = inter,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            )


        }
    }

}

@Preview(
    showBackground = true,
    backgroundColor = 0x00000000
)
@Composable
private fun TaskyTopBarPreview() {
    TaskyTheme {
        TaskyTopBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            date = ZonedDateTime.now().toInstant().toEpochMilli(),
            isInEditMode = false,
            onEnableEditing = { /*TODO*/ },
            onSave = {

            },
            onCancel = {

            }
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0x00000000
)
@Composable
private fun TaskyTopBarPreview2() {
    TaskyTheme {
        TaskyTopBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            date = ZonedDateTime.now().toInstant().toEpochMilli(),
            isInEditMode = true,
            onEnableEditing = { /*TODO*/ },
            onSave = {

            },
            onCancel = {

            }
        )
    }
}