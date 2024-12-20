package com.tasky.agenda.data.schedulers

import android.content.Context
import androidx.work.WorkManager
import androidx.work.await
import com.tasky.agenda.data.dao.ReminderDeleteSyncDao
import com.tasky.agenda.data.dao.ReminderSyncDao
import com.tasky.agenda.data.mappers.toReminderEntity
import com.tasky.agenda.data.model.ReminderDeleteSyncEntity
import com.tasky.agenda.data.model.ReminderSyncEntity
import com.tasky.agenda.data.model.SyncType
import com.tasky.agenda.data.schedulers.TaskWorkSyncScheduler.Companion.CREATE_TASK
import com.tasky.agenda.data.schedulers.util.WorkerType
import com.tasky.agenda.data.schedulers.util.toReminderOneTimeWorkRequest
import com.tasky.agenda.domain.schedulers.ReminderSyncScheduler
import com.tasky.core.domain.AuthInfoStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ReminderWorkSyncScheduler(
    context: Context,
    private val authInfoStorage: AuthInfoStorage,
    private val reminderSyncDao: ReminderSyncDao,
    private val reminderDeleteSyncDao: ReminderDeleteSyncDao
) : ReminderSyncScheduler {
    private val workManager = WorkManager.getInstance(context)
    override suspend fun sync(syncType: ReminderSyncScheduler.SyncType) {
        when (syncType) {
            is ReminderSyncScheduler.SyncType.CreateReminderSync -> {
                scheduleCreateReminderWorker(syncType)
            }

            is ReminderSyncScheduler.SyncType.DeleteReminderSync -> {
                scheduleDeleteReminderWorker(syncType)
            }

            is ReminderSyncScheduler.SyncType.UpdateReminderSync -> {
                scheduleUpdateReminderWorker(syncType)
            }
        }
    }

    override suspend fun cancelAllSyncs() {
        workManager
            .cancelAllWorkByTag(REMINDER_WORK)
            .await()
    }

    private suspend fun scheduleCreateReminderWorker(
        sync: ReminderSyncScheduler.SyncType.CreateReminderSync,
    ) {
        val userId = authInfoStorage.get()?.userId ?: return
        val reminderSyncEntity = ReminderSyncEntity(
            reminderId = sync.reminder.id,
            reminder = sync.reminder.toReminderEntity(),
            userId = userId,
            syncType = SyncType.CREATE
        )
        reminderSyncDao.upsertReminderPendingSync(reminderSyncEntity)
        workManager.enqueue(WorkerType.CREATE.toReminderOneTimeWorkRequest(reminderSyncEntity.reminderId))
            .await()
    }

    private suspend fun scheduleUpdateReminderWorker(
        sync: ReminderSyncScheduler.SyncType.UpdateReminderSync
    ) {
        val userId = authInfoStorage.get()?.userId ?: return
        val reminderSyncEntity = ReminderSyncEntity(
            reminderId = sync.reminder.id,
            reminder = sync.reminder.toReminderEntity(),
            userId = userId,
            syncType = SyncType.UPDATE
        )
        val pendingCreateReminderSync = reminderSyncDao.getReminderPendingSyncById(
            reminderId = sync.reminder.id,
            userId = userId,
            syncType = SyncType.CREATE
        )
        if (pendingCreateReminderSync != null) {
            workManager.cancelAllWorkByTag("$CREATE_REMINDER${pendingCreateReminderSync.reminderId}")
                .await()
            reminderSyncDao.upsertReminderPendingSync(
                reminderSyncEntity.copy(
                    syncType = SyncType.CREATE
                )
            )
            workManager.enqueue(
                WorkerType.CREATE.toReminderOneTimeWorkRequest(reminderSyncEntity.reminderId)
            ).await()
        } else {
            reminderSyncDao.upsertReminderPendingSync(reminderSyncEntity)
            workManager.enqueue(
                WorkerType.UPDATE.toReminderOneTimeWorkRequest(reminderSyncEntity.reminderId)
            ).await()
        }
    }

    private suspend fun scheduleDeleteReminderWorker(
        sync: ReminderSyncScheduler.SyncType.DeleteReminderSync
    ) {
        val userId = authInfoStorage.get()?.userId ?: return
        val reminderDeleteSyncEntity = ReminderDeleteSyncEntity(
            reminderId = sync.reminderId,
            userId = userId,
        )
        val pendingCreateReminderSync = reminderSyncDao.getReminderPendingSyncById(
            reminderId = sync.reminderId,
            userId = userId,
            syncType = SyncType.CREATE
        )

        val pendingUpdateReminderSync = reminderSyncDao.getReminderPendingSyncById(
            reminderId = sync.reminderId,
            userId = userId,
            syncType = SyncType.UPDATE
        )
        workManager.apply {
            if (pendingCreateReminderSync != null) {
                cancelAllWorkByTag("$CREATE_REMINDER${pendingCreateReminderSync.reminderId}").await()
                reminderSyncDao.deleteReminderPendingSyncById(
                    reminderId = sync.reminderId,
                    userId = userId,
                    syncType = SyncType.CREATE
                )
            }
            if (pendingUpdateReminderSync != null) {
                cancelAllWorkByTag("$UPDATE_REMINDER${pendingUpdateReminderSync.reminderId}").await()
                reminderSyncDao.deleteReminderPendingSyncById(
                    reminderId = sync.reminderId,
                    userId = userId,
                    syncType = SyncType.UPDATE
                )
            }
            if (pendingCreateReminderSync == null) {
                reminderDeleteSyncDao.upsertReminderDeletePendingSync(reminderDeleteSyncEntity)
                enqueue(WorkerType.DELETE.toReminderOneTimeWorkRequest(reminderDeleteSyncEntity.reminderId))
                    .await()
            }
        }
    }

    companion object {
        const val CREATE_REMINDER = "create_reminder"
        const val UPDATE_REMINDER = "update_reminder"
        const val DELETE_REMINDER = "delete_reminder"
        const val REMINDER_WORK = "reminder_work"
    }

}