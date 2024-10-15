package com.tasky.agenda.presentation.agenda_item_details.model

data class VisitorState(
    val email: String = "",
    val isValidEmail: Boolean = false,
    val isLoading: Boolean = false
)
