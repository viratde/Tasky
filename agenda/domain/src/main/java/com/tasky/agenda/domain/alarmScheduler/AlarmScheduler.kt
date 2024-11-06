package com.tasky.agenda.domain.alarmScheduler

import com.tasky.agenda.domain.model.Alarm

interface AlarmScheduler {

    suspend fun scheduleAlarm(alarm: Alarm)

    suspend fun scheduleAlarms(alarm: List<Alarm>)

    suspend fun cancelAlarmById(alarmId: String)

}