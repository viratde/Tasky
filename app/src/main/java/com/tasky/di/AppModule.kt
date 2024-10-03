package com.tasky.di

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import org.koin.android.ext.koin.androidApplication
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
    }
