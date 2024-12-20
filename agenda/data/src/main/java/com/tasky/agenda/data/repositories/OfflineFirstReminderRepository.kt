package com.tasky.agenda.data.repositories

import com.tasky.agenda.domain.alarmScheduler.AlarmScheduler
import com.tasky.agenda.domain.data_sources.local.LocalReminderDataSource
import com.tasky.agenda.domain.data_sources.remote.RemoteReminderDataSource
import com.tasky.agenda.domain.mappers.toAlarm
import com.tasky.agenda.domain.model.Reminder
import com.tasky.agenda.domain.repository.ReminderRepository
import com.tasky.agenda.domain.schedulers.ReminderSyncScheduler
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import com.tasky.core.domain.util.Result
import com.tasky.core.domain.util.asEmptyDataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class OfflineFirstReminderRepository(
    private val localReminderDataSource: LocalReminderDataSource,
    private val remoteReminderDataSource: RemoteReminderDataSource,
    private val reminderSyncScheduler: ReminderSyncScheduler,
    private val alarmScheduler: AlarmScheduler,
    private val applicationScope: CoroutineScope
) : ReminderRepository {

    override suspend fun addReminder(reminder: Reminder): EmptyDataResult<DataError> {
        val localReminderResult = localReminderDataSource.upsertReminder(reminder)
        if (localReminderResult !is Result.Success) {
            return localReminderResult.asEmptyDataResult()
        }
        alarmScheduler.scheduleAlarm(reminder.toAlarm())
        return when (val remoteReminderResult = remoteReminderDataSource.create(reminder)) {
            is Result.Error -> {
                applicationScope.launch {
                    reminderSyncScheduler.sync(
                        ReminderSyncScheduler.SyncType.CreateReminderSync(
                            reminder
                        )
                    )
                }.join()
                Result.Success(Unit)
            }

            is Result.Success -> {
                remoteReminderResult.asEmptyDataResult()
            }
        }
    }

    override suspend fun updateReminder(reminder: Reminder): EmptyDataResult<DataError> {
        val localReminderResult = localReminderDataSource.upsertReminder(reminder)
        if (localReminderResult !is Result.Success) {
            return localReminderResult.asEmptyDataResult()
        }
        alarmScheduler.scheduleAlarm(reminder.toAlarm())
        return when (val remoteReminderResult =
            remoteReminderDataSource.update(reminder)) {
            is Result.Error -> {
                applicationScope.launch {
                    reminderSyncScheduler.sync(
                        ReminderSyncScheduler.SyncType.UpdateReminderSync(
                            reminder
                        )
                    )
                }.join()
                Result.Success(Unit)
            }

            is Result.Success -> {
                remoteReminderResult.asEmptyDataResult()
            }
        }
    }

    override suspend fun getRemindersByTime(time: Long): Flow<List<Reminder>> {
        return localReminderDataSource.getRemindersByTime(time)
    }

    override suspend fun deleteRemindersById(reminderId: String) {
        localReminderDataSource.deleteReminder(reminderId)
        alarmScheduler.cancelAlarmById(reminderId)
        val result = remoteReminderDataSource.delete(reminderId)
        if (result is Result.Error) {
            applicationScope.launch {
                reminderSyncScheduler.sync(ReminderSyncScheduler.SyncType.DeleteReminderSync(reminderId))
            }.join()
        }
    }

    override suspend fun getReminderById(reminderId: String): Reminder? {
        return localReminderDataSource.getReminderById(reminderId)
    }

    override suspend fun getAllRemindersGraterThanTime(time: Long): List<Reminder> {
        return localReminderDataSource.getAllRemindersGreaterThanTime(time)
    }

}