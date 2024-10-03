package com.tasky.core.presentation.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed interface UiText {

    data class DynamicString(val value: String) : UiText

    data class StringResource(
        val id: Int,
        val args: List<Any> = listOf()
    ) : UiText


    @Composable
    fun asText(): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> stringResource(id = id, args)
        }
    }


    fun asText(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(id, args)
        }
    }

}
