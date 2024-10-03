package com.tasky.auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasky.auth.domain.AuthRepository
import com.tasky.auth.domain.PatternValidator
import com.tasky.auth.presentation.R
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.onError
import com.tasky.core.domain.util.onSuccess
import com.tasky.core.presentation.ui.UiText
import com.tasky.core.presentation.ui.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val patternValidator: PatternValidator,
) : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    private val _events = Channel<LoginEvent>()

    val events = _events.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnEmailChange -> {
                state =
                    state.copy(
                        email = action.email,
                        isValidEmail = patternValidator.matches(action.email),
                    )
            }

            is LoginAction.OnPasswordChange -> {
                state =
                    state.copy(
                        password = action.password,
                    )
            }

            LoginAction.OnTogglePasswordVisibility -> {
                state =
                    state.copy(
                        isPasswordVisible = !state.isPasswordVisible,
                    )
            }

            LoginAction.OnLogin -> {
                login()
            }

            LoginAction.OnNavigateToSignUpScreen -> {
                // handled in ui layer
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            state = state.copy(isLoggingIn = true)
            val result = authRepository.login(state.email, state.password)
            state = state.copy(isLoggingIn = false)

            result.onSuccess {
                _events.send(LoginEvent.OnLoginSuccess)
            }.onError { error ->
                if (error == DataError.Network.UNAUTHORIZED) {
                    _events.send(LoginEvent.OnError(UiText.StringResource(R.string.error_email_password_incorrect)))
                } else {
                    _events.send(LoginEvent.OnError(error.asUiText()))
                }
            }
        }
    }
}
