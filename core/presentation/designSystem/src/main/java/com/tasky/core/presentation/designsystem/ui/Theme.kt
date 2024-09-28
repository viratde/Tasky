package com.tasky.core.presentation.designsystem.ui

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat


@Composable
fun TaskyTheme(
    content: @Composable () -> Unit
) {

    val activity = LocalContext.current as? Activity

    LaunchedEffect(key1 = Unit) {
        if (activity != null) {
            WindowCompat.getInsetsController(
                activity.window,
                activity.window.decorView
            ).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        typography = typography,
        colorScheme = taskyColorScheme
    ) {
        content()
    }


}