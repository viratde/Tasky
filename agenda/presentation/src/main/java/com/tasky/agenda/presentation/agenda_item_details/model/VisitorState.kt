package com.tasky.agenda.presentation.common.model

data class VisitorState(
    val email: String = "",
    val isValidEmail: Boolean = false,
    val isLoading: Boolean = false
)
