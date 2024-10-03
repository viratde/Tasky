package com.tasky.auth.presentation.login

sealed interface LoginAction {
    data class OnEmailChange(val email: String) : LoginAction

    data class OnPasswordChange(val password: String) : LoginAction

    data object OnTogglePasswordVisibility : LoginAction

    data object OnLogin : LoginAction

    data object OnNavigateToSignUpScreen : LoginAction
}
