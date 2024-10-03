package com.tasky.auth.presentation.di

import com.tasky.auth.presentation.login.LoginViewModel
import com.tasky.auth.presentation.signup.SignUpViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationModule =
    module {
        viewModelOf(::LoginViewModel)
        viewModelOf(::SignUpViewModel)
    }
