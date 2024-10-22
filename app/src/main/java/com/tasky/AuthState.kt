package com.tasky

data class AuthState(
    val isCheckingAuth: Boolean = false,
    val isLoggedIn: Boolean = false
)
