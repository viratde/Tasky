package com.tasky.auth.data.di

import com.tasky.auth.data.AuthRepositoryImpl
import com.tasky.auth.data.EmailPatternValidator
import com.tasky.auth.domain.AuthRepository
import com.tasky.auth.domain.PatternValidator
import com.tasky.auth.domain.UserDataValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authDataModule =
    module {

        singleOf(::AuthRepositoryImpl).bind<AuthRepository>()

        singleOf(::EmailPatternValidator).bind<PatternValidator>()

        singleOf(::UserDataValidator)
    }
