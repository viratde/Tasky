package com.tasky.auth.presentation.signup

sealed interface SignUpAction {
    data class OnNameChange(val name: String) : SignUpAction

    data class OnEmailChange(val email: String) : SignUpAction

    data class OnPasswordChange(val password: String) : SignUpAction

    data object OnTogglePasswordVisibility : SignUpAction

    data object OnSignUp : SignUpAction

    data object OnBack : SignUpAction
}
