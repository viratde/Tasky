package com.tasky.di

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.tasky.AlarmSchedulerImpl
import com.tasky.AuthViewModel
import com.tasky.TaskyApp
import com.tasky.agenda.domain.alarmScheduler.AlarmScheduler
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule =
    module {
        single<SharedPreferences> {
            EncryptedSharedPreferences(
                context = androidApplication(),
                fileName = "auth_prefs",
                masterKey = MasterKey(androidApplication()),
                prefKeyEncryptionScheme = EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                prefValueEncryptionScheme = EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
            )
        }

        single {
            (androidApplication() as TaskyApp).applicationScope
        }

        viewModelOf(::AuthViewModel)

        singleOf(::AlarmSchedulerImpl).bind<AlarmScheduler>()
    }
