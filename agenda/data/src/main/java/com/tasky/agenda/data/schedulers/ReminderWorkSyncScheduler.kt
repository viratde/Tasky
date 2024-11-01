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
    private val reminderDeleteSyncDao: ReminderDeleteSyncDao,
    private val applicationScope: CoroutineScope
) : ReminderSyncScheduler {
    private val workManager = WorkManager.getInstance(context)
    override suspend fun sync(syncType: ReminderSyncScheduler.SyncType) {
        when (syncType) {
            is ReminderSyncScheduler.SyncType.CreateReminderSync -> {
                scheduleCreateTaskWorker(syncType)
            }

            is ReminderSyncScheduler.SyncType.DeleteReminderSync -> {
                scheduleDeleteTaskWorker(syncType)
            }

            is ReminderSyncScheduler.SyncType.UpdateReminderSync -> {
                scheduleUpdateTaskWorker(syncType)
            }
        }
    }

    override suspend fun cancelAllSyncs() {
        workManager
            .cancelAllWorkByTag(REMINDER_WORK)
            .await()
    }

    private suspend fun scheduleCreateTaskWorker(
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
        applicationScope.launch {
            workManager.enqueue(WorkerType.CREATE.toReminderOneTimeWorkRequest(reminderSyncEntity.reminderId))
                .await()
        }.join()
    }

    private suspend fun scheduleUpdateTaskWorker(
        sync: ReminderSyncScheduler.SyncType.UpdateReminderSync
    ) {
        val userId = authInfoStorage.get()?.userId ?: return
        val reminderSyncEntity = ReminderSyncEntity(
            reminderId = sync.reminder.id,
            reminder = sync.reminder.toReminderEntity(),
            userId = userId,
            syncType = SyncType.CREATE
        )
        reminderSyncDao.upsertReminderPendingSync(reminderSyncEntity)
        val pendingCreateReminderSync = reminderSyncDao.getReminderPendingSyncById(
            reminderId = sync.reminder.id,
            userId = userId,
            syncType = SyncType.CREATE
        )
        applicationScope.launch {
            if (pendingCreateReminderSync != null) {
                workManager.cancelAllWorkByTag("$CREATE_REMINDER${pendingCreateReminderSync.reminderId}")
                    .await()
                workManager.beginWith(
                    WorkerType.CREATE.toReminderOneTimeWorkRequest(pendingCreateReminderSync.reminderId)
                ).then(
                    WorkerType.UPDATE.toReminderOneTimeWorkRequest(reminderSyncEntity.reminderId)
                ).enqueue()
            } else {
                workManager.enqueue(
                    WorkerType.UPDATE.toReminderOneTimeWorkRequest(reminderSyncEntity.reminderId)
                ).await()
            }
        }.join()
    }

    private suspend fun scheduleDeleteTaskWorker(
        sync: ReminderSyncScheduler.SyncType.DeleteReminderSync
    ) {
        val userId = authInfoStorage.get()?.userId ?: return
        val reminderDeleteSyncEntity = ReminderDeleteSyncEntity(
            reminderId = sync.reminderId,
            userId = userId,
        )
        reminderDeleteSyncDao.upsertReminderDeletePendingSync(reminderDeleteSyncEntity)
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
        applicationScope.launch {
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
                if(pendingCreateReminderSync == null){
                    enqueue(WorkerType.DELETE.toReminderOneTimeWorkRequest(reminderDeleteSyncEntity.reminderId))
                        .await()
                }
            }
        }.join()
    }

    companion object {
        const val CREATE_REMINDER = "create_reminder"
        const val UPDATE_REMINDER = "update_reminder"
        const val DELETE_REMINDER = "delete_reminder"
        const val REMINDER_WORK = "task_work"
    }

}