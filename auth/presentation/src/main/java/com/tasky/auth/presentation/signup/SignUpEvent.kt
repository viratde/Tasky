package com.tasky.auth.presentation.login

sealed interface LoginEvent {
    data object OnLoginSuccess : LoginEvent
    data class OnShowMessage(val message: String) : LoginEvent
}