package com.tasky

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasky.core.domain.AuthInfoStorage
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authInfoStorage: AuthInfoStorage
) : ViewModel() {

    var state by mutableStateOf(AuthState())
        private set

    init {
        viewModelScope.launch {

            state = state.copy(isCheckingAuth = true)

            val authInfo = authInfoStorage.get()

            state = state.copy(
                isCheckingAuth = false,
                isLoggedIn = authInfo != null
            )

        }
    }

}