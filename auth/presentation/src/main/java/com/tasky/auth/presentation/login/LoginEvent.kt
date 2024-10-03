package com.tasky.auth.presentation.login

import com.tasky.core.presentation.ui.UiText

sealed interface LoginEvent {
    data object OnLoginSuccess : LoginEvent

    data class OnError(val message: UiText) : LoginEvent
}
