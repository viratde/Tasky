package com.tasky.common

import android.app.Application
import androidx.test.platform.app.InstrumentationRegistry
import com.tasky.auth.data.di.authDataModule
import com.tasky.auth.presentation.di.authPresentationModule
import com.tasky.core.data.di.coreDataModule
import com.tasky.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TaskyTestApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(InstrumentationRegistry.getInstrumentation().targetContext.applicationContext)
            modules(
                authPresentationModule,
                authDataModule,
                coreDataModule,
                appModule,
            )
        }
    }
}
