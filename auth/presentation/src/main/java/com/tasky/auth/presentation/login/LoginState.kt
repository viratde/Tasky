package com.tasky.auth.presentation.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isValidEmail: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isEmailError: Boolean = false,
    val isPasswordError: Boolean = false,
    val isLoggingIn: Boolean = false
)
