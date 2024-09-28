package com.tasky.auth.presentation.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {

    var state by mutableStateOf(SignUpState())
        private set


    fun onAction(action: SignUpAction) {

    }

}