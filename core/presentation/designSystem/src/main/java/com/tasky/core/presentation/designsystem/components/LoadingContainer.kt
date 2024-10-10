package com.tasky.core.presentation.designsystem.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tasky.core.presentation.designsystem.ui.TaskyBlack
import com.tasky.core.presentation.designsystem.ui.TaskyGrey
import com.tasky.core.presentation.designsystem.ui.TaskyWhite

@Composable
fun LoadingContainer(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    content: @Composable () -> Unit
) {

    AnimatedContent(
        targetState = isLoading,
        label = "Loading Content Animation",
        transitionSpec = {
            fadeIn() togetherWith fadeOut()
        }
    ) { loading ->
        if (loading) {
            Column(
                modifier = modifier
                    .background(TaskyWhite),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    color = TaskyBlack,
                    trackColor = TaskyGrey,
                    strokeWidth = 3.dp,
                    modifier = Modifier
                        .size(70.dp)
                )
            }
        } else {
            content()
        }
    }

}