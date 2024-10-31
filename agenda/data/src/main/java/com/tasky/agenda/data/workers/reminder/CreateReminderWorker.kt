package com.tasky.agenda.data.workers.reminder

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.tasky.agenda.data.dao.ReminderSyncDao
import com.tasky.agenda.data.dao.TaskSyncDao
import com.tasky.agenda.data.mappers.toReminder
import com.tasky.agenda.data.mappers.toTask
import com.tasky.agenda.data.model.SyncType
import com.tasky.agenda.domain.data_sources.remote.RemoteReminderDataSource
import com.tasky.agenda.domain.data_sources.remote.RemoteTaskDataSource
import com.tasky.core.data.utils.toWorkerResult
import com.tasky.core.domain.AuthInfoStorage

class CreateReminderWorker(
    context: Context,
    private val params: WorkerParameters,
    private val authInfoStorage: AuthInfoStorage,
    private val reminderSyncDao: ReminderSyncDao,
    private val remoteReminderDataSource: RemoteReminderDataSource
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        if (runAttemptCount > 5) {
            return Result.failure()
        }

        val reminderId = params.inputData.getString(REMINDER_ID) ?: return Result.failure()
        val userId = authInfoStorage.get()?.userId ?: return Result.failure()
        val createRemoteSyncEntity = reminderSyncDao.getReminderPendingSyncById(
            reminderId = reminderId,
            userId = userId,
            syncType = SyncType.CREATE
        ) ?: return Result.failure()


        return when (val result =
            remoteReminderDataSource.create(createRemoteSyncEntity.reminder.toReminder())) {
            is com.tasky.core.domain.util.Result.Error -> {
                result.error.toWorkerResult()
            }

            is com.tasky.core.domain.util.Result.Success -> {
                reminderSyncDao.deleteReminderPendingSyncById(
                    reminderId = reminderId,
                    userId = userId,
                    syncType = SyncType.CREATE
                )
                Result.success()
            }
        }

    }

    companion object {
        const val REMINDER_ID = "reminder_id"
        const val TAG = "create_reminder_work"
    }
}