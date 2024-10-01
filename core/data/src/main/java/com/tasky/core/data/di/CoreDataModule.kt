package com.tasky.core.data.di

import com.tasky.core.data.auth.AuthInfoStorageImpl
import com.tasky.core.data.networking.HttpClientFactory
import com.tasky.core.domain.AuthInfoStorage
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {

    single {
        AuthInfoStorageImpl(get())
    }.bind<AuthInfoStorage>()

    single { HttpClientFactory(get()).build() }


}