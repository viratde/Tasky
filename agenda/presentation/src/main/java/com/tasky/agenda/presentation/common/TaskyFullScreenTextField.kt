package com.tasky.agenda.presentation.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import com.tasky.core.presentation.designsystem.ui.TaskyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskyFullScreenTextField(
    modifier: Modifier = Modifier
) {


    var isOpen by remember {
        mutableStateOf(true)
    }


    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    Button(onClick = { isOpen = true }) {
        Text(text = "Open")
    }

    if (isOpen) {
        ModalBottomSheet(
            onDismissRequest = { isOpen = false },
            shape = RectangleShape,
            sheetState = sheetState,
            dragHandle = null
        ) {

            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxSize()
            )

        }
    }

}


@Preview
@Composable
private fun TaskyFullScreenTextFieldPreview() {
    TaskyTheme {
        TaskyFullScreenTextField()
    }
}