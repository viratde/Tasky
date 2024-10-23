package com.tasky.core.presentation.designsystem.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.tasky.core.presentation.designsystem.ui.TaskyWhite

@Composable
fun TaskyDropDownMenu(
    expanded: Boolean,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit = {}
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onClose,
        containerColor = TaskyWhite,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier,
        properties = PopupProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        )
    ) {
        content()
    }
}
