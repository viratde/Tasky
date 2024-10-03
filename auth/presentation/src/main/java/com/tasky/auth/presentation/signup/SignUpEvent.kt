package com.tasky.auth.presentation.signup

import com.tasky.core.presentation.ui.UiText

sealed interface SignUpEvent {
    data object OnSignUpSuccess : SignUpEvent

    data class OnError(val message: UiText) : SignUpEvent
}
