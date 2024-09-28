package com.tasky.auth.presentation.signup

sealed interface SignUpEvent {
    data object OnSignUpSuccess : SignUpEvent
    data class OnShowSuccessMessage(val message: String) : SignUpEvent
    data class OnShowErrorMessage(val message: String) : SignUpEvent
}