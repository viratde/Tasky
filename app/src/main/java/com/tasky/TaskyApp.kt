package com.tasky

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.tasky.agenda.data.alarmScheduler.AlarmReceiver
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
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import timber.log.Timber

class TaskyApp : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            workManagerFactory()
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
        createReminderNotificationChannel()
    }

    private fun createReminderNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                AlarmReceiver.REMINDER_NOTIFICATION_CHANNEL_ID,
                AlarmReceiver.REMINDER_NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableVibration(true)
                enableLights(true)
                description = applicationContext.getString(R.string.reminder_notification_channel_desc)
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

}
