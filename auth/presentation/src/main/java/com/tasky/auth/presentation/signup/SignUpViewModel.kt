package com.tasky.auth.presentation.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasky.auth.domain.AuthRepository
import com.tasky.auth.domain.UserDataValidator
import com.tasky.auth.presentation.R
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.onError
import com.tasky.core.domain.util.onSuccess
import com.tasky.core.presentation.ui.UiText
import com.tasky.core.presentation.ui.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val userDataValidator: UserDataValidator,
    private val authRepository: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(SignUpState())
        private set

    private val _events = Channel<SignUpEvent>()

    val events = _events.receiveAsFlow()

    fun onAction(action: SignUpAction) {
        when (action) {
            SignUpAction.OnBack -> {
                // handled in ui layer
            }

            is SignUpAction.OnEmailChange -> {
                state = state.copy(
                    email = action.email,
                    isValidEmail = userDataValidator.isValidEmail(action.email)
                )
            }

            is SignUpAction.OnNameChange -> {
                state = state.copy(
                    name = action.name,
                    isValidName = userDataValidator.isValidFullName(action.name)
                )
            }

            is SignUpAction.OnPasswordChange -> {
                state = state.copy(
                    password = action.password,
                    isValidPassword = userDataValidator.isValidPassword(action.password)
                )
            }

            SignUpAction.OnSignUp -> {
                register()
            }

            SignUpAction.OnTogglePasswordVisibility -> {
                state = state.copy(
                    isPasswordVisible = !state.isPasswordVisible
                )
            }
        }

    }

    private fun register() {

        viewModelScope.launch {

            state = state.copy(isSigningUp = true)

            val response = authRepository.register(
                fullName = state.name,
                email = state.email,
                password = state.password
            )

            state = state.copy(isSigningUp = false)

            response.onSuccess {
                _events.send(SignUpEvent.OnSignUpSuccess)
            }.onError { error ->
                if (error == DataError.Network.CONFLICT) {
                    _events.send(SignUpEvent.OnError(UiText.StringResource(R.string.user_already_exists)))
                }
                _events.send(SignUpEvent.OnError(error.asUiText()))
            }

        }

    }

}