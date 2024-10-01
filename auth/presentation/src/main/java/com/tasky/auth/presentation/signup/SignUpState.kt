package com.tasky.auth.presentation.signup

data class SignUpState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isValidName: Boolean = false,
    val isValidEmail: Boolean = false,
    val isValidPassword:Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isEmailError: Boolean = false,
    val isSigningUp: Boolean = false
)
