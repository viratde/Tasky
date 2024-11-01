package com.tasky.agenda.data.workers.reminder

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.tasky.agenda.data.dao.ReminderDeleteSyncDao
import com.tasky.agenda.data.dao.ReminderSyncDao
import com.tasky.agenda.data.mappers.toReminder
import com.tasky.agenda.data.model.SyncType
import com.tasky.agenda.domain.data_sources.remote.RemoteReminderDataSource
import com.tasky.core.data.utils.toWorkerResult
import com.tasky.core.domain.AuthInfoStorage

class DeleteReminderWorker(
    context: Context,
    private val params: WorkerParameters,
    private val authInfoStorage: AuthInfoStorage,
    private val reminderSyncDao: ReminderSyncDao,
    private val reminderDeleteSyncDao: ReminderDeleteSyncDao,
    private val remoteReminderDataSource: RemoteReminderDataSource
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        if (runAttemptCount > 5) {
            return Result.failure()
        }

        val reminderId = params.inputData.getString(REMINDER_ID) ?: return Result.failure()
        val userId = authInfoStorage.get()?.userId ?: return Result.failure()
        val deleteReminderSyncEntity = reminderDeleteSyncDao.getReminderDeletePendingSyncById(
            reminderId = reminderId,
            userId = userId
        ) ?: return Result.failure()

        val preScheduledCreateReminderSync = reminderSyncDao.getReminderPendingSyncById(
            reminderId = reminderId,
            userId = userId,
            syncType = SyncType.CREATE
        )

        if (preScheduledCreateReminderSync != null) {
            when (val result =
                remoteReminderDataSource.create(preScheduledCreateReminderSync.reminder.toReminder())) {
                is com.tasky.core.domain.util.Result.Error -> {
                    result.error.toWorkerResult()
                }

                is com.tasky.core.domain.util.Result.Success -> {
                    reminderSyncDao.deleteReminderPendingSyncById(
                        reminderId = reminderId,
                        userId = userId,
                        syncType = SyncType.CREATE
                    )
                }
            }
        }

        val preScheduledUpdateReminderSync = reminderSyncDao.getReminderPendingSyncById(
            reminderId = reminderId,
            userId = userId,
            syncType = SyncType.UPDATE
        )

        if (preScheduledUpdateReminderSync != null) {
            when (val result =
                remoteReminderDataSource.update(preScheduledUpdateReminderSync.reminder.toReminder())) {
                is com.tasky.core.domain.util.Result.Error -> {
                    result.error.toWorkerResult()
                }

                is com.tasky.core.domain.util.Result.Success -> {
                    reminderSyncDao.deleteReminderPendingSyncById(
                        reminderId = reminderId,
                        userId = userId,
                        syncType = SyncType.UPDATE
                    )
                    Result.success()
                }
            }
        }

        return when (val result =
            remoteReminderDataSource.delete(deleteReminderSyncEntity.reminderId)) {
            is com.tasky.core.domain.util.Result.Error -> {
                result.error.toWorkerResult()
            }

            is com.tasky.core.domain.util.Result.Success -> {
                reminderDeleteSyncDao.deleteReminderDeletePendingSyncById(
                    reminderId = reminderId,
                    userId = userId,
                )
                Result.success()
            }
        }

    }

    companion object {
        const val REMINDER_ID = "reminder_id"
        const val TAG = "delete_reminder_work"
    }
}