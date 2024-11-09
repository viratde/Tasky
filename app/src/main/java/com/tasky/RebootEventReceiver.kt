package com.tasky

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.tasky.agenda.domain.alarmScheduler.AlarmScheduler
import com.tasky.agenda.domain.mappers.toAlarm
import com.tasky.agenda.domain.repository.AgendaRepository
import com.tasky.core.presentation.ui.getCurrentTimeInMillis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class RebootEventReceiver : BroadcastReceiver() {

    private val agendaRepository: AgendaRepository by inject(
        clazz = AgendaRepository::class.java
    )

    private val alarmScheduler: AlarmScheduler by inject(
        clazz = AlarmScheduler::class.java
    )

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            context?.run {
                CoroutineScope(Dispatchers.IO).launch {
                    val alarms = agendaRepository
                        .getAllAgendaItemsGraterThanTime(getCurrentTimeInMillis())
                        .let {
                            it.events.map { it.toAlarm() } + it.tasks.map { it.toAlarm() } + it.reminders.map { it.toAlarm() }
                        }
                    alarmScheduler.scheduleAlarms(alarms)
                }
            }
        }
    }

}