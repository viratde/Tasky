package com.tasky

import android.app.Application
import com.tasky.auth.data.di.authDataModule
import com.tasky.auth.presentation.di.authPresentationModule
import com.tasky.core.data.di.coreDataModule
import com.tasky.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TaskyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(
                authPresentationModule,
                authDataModule,
                coreDataModule,
                appModule
            )
        }
    }

}