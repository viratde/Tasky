package com.tasky

import android.app.Application
import com.tasky.agenda.data.di.agendaDataModule
import com.tasky.agenda.network.di.agendaNetworkModule
import com.tasky.agenda.presentation.di.agendaPresentationModule
import com.tasky.auth.data.di.authDataModule
import com.tasky.auth.presentation.di.authPresentationModule
import com.tasky.core.data.di.coreDataModule
import com.tasky.di.appModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TaskyApp : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(
                authPresentationModule,
                authDataModule,
                coreDataModule,
                appModule,
                agendaDataModule,
                agendaNetworkModule,
                agendaPresentationModule
            )
        }
    }

}
