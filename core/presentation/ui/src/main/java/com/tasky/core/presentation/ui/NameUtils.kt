package com.tasky.core.presentation.ui

fun String.formattedUiName(): String {
    if (this.isEmpty()) return ""
    val parts = this.split("\\s+".toRegex())
    return when (parts.size) {
        1 -> {
            parts.first().slice(0..1).uppercase()
        }

        else -> {
            parts.first().first().toString().uppercase() + parts.last().first().toString()
                .uppercase()
        }
    }
}