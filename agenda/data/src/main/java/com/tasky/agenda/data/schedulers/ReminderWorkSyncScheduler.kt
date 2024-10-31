package com.tasky.agenda.data.schedulers

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.await
import com.tasky.agenda.data.dao.ReminderDeleteSyncDao
import com.tasky.agenda.data.dao.ReminderSyncDao
import com.tasky.agenda.data.mappers.toReminderEntity
import com.tasky.agenda.data.model.ReminderDeleteSyncEntity
import com.tasky.agenda.data.model.ReminderSyncEntity
import com.tasky.agenda.data.model.SyncType
import com.tasky.agenda.data.workers.reminder.CreateReminderWorker
import com.tasky.agenda.data.workers.reminder.DeleteReminderWorker
import com.tasky.agenda.data.workers.reminder.UpdateReminderWorker
import com.tasky.agenda.domain.schedulers.ReminderSyncScheduler
import com.tasky.core.data.utils.setExponentialBackOffPolicy
import com.tasky.core.data.utils.setInputParameters
import com.tasky.core.data.utils.setRequiredNetworkConnectivity
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
        val workRequest = OneTimeWorkRequestBuilder<CreateReminderWorker>()
            .addTag("$CREATE_REMINDER${reminderSyncEntity.reminderId}")
            .addTag(REMINDER_WORK)
            .addTag(CreateReminderWorker.TAG)
            .setRequiredNetworkConnectivity()
            .setExponentialBackOffPolicy(2000)
            .setInputParameters { putString(CreateReminderWorker.REMINDER_ID, reminderSyncEntity.reminderId) }
            .build()

        applicationScope.launch {
            workManager.enqueue(workRequest).await()
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
        val workRequest = OneTimeWorkRequestBuilder<UpdateReminderWorker>()
            .addTag("$UPDATE_REMINDER${reminderSyncEntity.reminderId}")
            .addTag(REMINDER_WORK)
            .addTag(UpdateReminderWorker.TAG)
            .setRequiredNetworkConnectivity()
            .setExponentialBackOffPolicy(2000)
            .setInputParameters {
                putString(
                    UpdateReminderWorker.REMINDER_ID,
                    reminderSyncEntity.reminderId
                )
            }
            .build()

        applicationScope.launch {
            workManager.enqueue(workRequest).await()
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
        val workRequest = OneTimeWorkRequestBuilder<DeleteReminderWorker>()
            .addTag("$DELETE_REMINDER${reminderDeleteSyncEntity.reminderId}")
            .addTag(REMINDER_WORK)
            .addTag(DeleteReminderWorker.TAG)
            .setRequiredNetworkConnectivity()
            .setExponentialBackOffPolicy(2000)
            .setInputParameters {
                putString(
                    DeleteReminderWorker.REMINDER_ID,
                    reminderDeleteSyncEntity.reminderId
                )
            }
            .build()

        applicationScope.launch {
            workManager.enqueue(workRequest).await()
        }.join()
    }

    companion object {
        const val CREATE_REMINDER = "create_reminder"
        const val UPDATE_REMINDER = "update_reminder"
        const val DELETE_REMINDER = "delete_reminder"
        const val REMINDER_WORK = "task_work"
    }

}