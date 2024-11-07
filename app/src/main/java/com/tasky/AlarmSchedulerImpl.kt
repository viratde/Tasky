package com.tasky

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.tasky.agenda.domain.alarmScheduler.AlarmScheduler
import com.tasky.agenda.domain.model.Alarm
import timber.log.Timber
import java.time.Instant

class AlarmSchedulerImpl(
    private val context: Context
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override suspend fun scheduleAlarm(alarm: Alarm) {
        if (alarm.at <= Instant.now().toEpochMilli()) return
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(AlarmReceiver.ID, alarm.id)
            putExtra(AlarmReceiver.TITLE, alarm.title)
            putExtra(AlarmReceiver.DESCRIPTION, alarm.description)
        }
        if (alarmManager.hasExactAlarmPermission()) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                alarm.at,
                PendingIntent.getBroadcast(
                    context,
                    alarm.id.hashCode(),
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )
        } else {
            Timber.d("App Does not have the permission to schedule exact alarms.")
        }
    }

    override suspend fun scheduleAlarms(alarm: List<Alarm>) {
        alarm.forEach {
            scheduleAlarm(it)
        }
    }

    override suspend fun cancelAlarmById(alarmId: String) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                alarmId.hashCode(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    private fun AlarmManager.hasExactAlarmPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            canScheduleExactAlarms()
        } else {
            true
        }
    }


}