package com.tasky

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasky.core.domain.AuthInfoStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authInfoStorage: AuthInfoStorage
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())

    val state = _state
        .onStart {
            checkIsUserLoggedIn()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            AuthState(
                isCheckingAuth = true
            )
        )

    private fun checkIsUserLoggedIn() {
        viewModelScope.launch {
            _state.update { it.copy(isCheckingAuth = true) }
            _state.update {
                it.copy(
                    isCheckingAuth = false,
                    isLoggedIn = authInfoStorage.get() != null
                )
            }
        }
    }

}