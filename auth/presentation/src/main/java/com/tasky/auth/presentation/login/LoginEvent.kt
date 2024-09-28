package com.tasky.auth.presentation.login

import com.tasky.auth.presentation.signup.SignUpEvent

sealed interface LoginEvent {
    data object OnLoginSuccess : LoginEvent
    data class OnShowSuccessMessage(val message: String) : LoginEvent
    data class OnShowErrorMessage(val message: String) : LoginEvent
}