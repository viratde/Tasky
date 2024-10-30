package com.tasky.agenda.data.schedulers

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.await
import com.tasky.agenda.data.dao.TaskSyncDao
import com.tasky.agenda.data.mappers.toTaskEntity
import com.tasky.agenda.data.model.SyncType
import com.tasky.agenda.data.model.TaskDeleteSyncEntity
import com.tasky.agenda.data.model.TaskSyncEntity
import com.tasky.agenda.data.workers.task.CreateTaskWorker
import com.tasky.agenda.data.workers.task.DeleteTaskWorker
import com.tasky.agenda.data.workers.task.UpdateTaskWorker
import com.tasky.agenda.domain.schedulers.TaskSyncScheduler
import com.tasky.core.data.utils.setExponentialBackOffPolicy
import com.tasky.core.data.utils.setInputParameters
import com.tasky.core.data.utils.setRequiredNetworkConnectivity
import com.tasky.core.domain.AuthInfoStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class TaskWorkSyncScheduler(
    context: Context,
    private val authInfoStorage: AuthInfoStorage,
    private val taskSyncDao: TaskSyncDao,
    private val applicationScope: CoroutineScope
) : TaskSyncScheduler {


    private val workManager = WorkManager.getInstance(context)

    override suspend fun sync(syncType: TaskSyncScheduler.SyncType) {
        when (syncType) {
            is TaskSyncScheduler.SyncType.CreateTaskSync -> {
                scheduleCreateTaskWorker(syncType)
            }

            is TaskSyncScheduler.SyncType.DeleteTaskSync -> {
                scheduleDeleteTaskWorker(syncType)
            }

            is TaskSyncScheduler.SyncType.UpdateTaskSync -> {
                scheduleUpdateTaskWorker(syncType)
            }
        }
    }

    override suspend fun cancelAllSyncs() {
        workManager
            .cancelAllWorkByTag(TASK_WORK)
            .await()
    }

    private suspend fun scheduleCreateTaskWorker(
        sync: TaskSyncScheduler.SyncType.CreateTaskSync,
    ) {

        val userId = authInfoStorage.get()?.userId ?: return
        val taskSyncEntity = TaskSyncEntity(
            taskId = sync.task.id,
            task = sync.task.toTaskEntity(),
            userId = userId,
            syncType = SyncType.CREATE
        )

        taskSyncDao.upsertTaskPendingSync(taskSyncEntity)

        val workRequest = OneTimeWorkRequestBuilder<CreateTaskWorker>()
            .addTag("$CREATE_TASK${taskSyncEntity.taskId}")
            .addTag(TASK_WORK)
            .setRequiredNetworkConnectivity()
            .setExponentialBackOffPolicy(2000)
            .setInputParameters { putString(CreateTaskWorker.TASK_ID, taskSyncEntity.taskId) }
            .build()

        applicationScope.launch {
            workManager.enqueue(workRequest).await()
        }.join()
    }

    private suspend fun scheduleUpdateTaskWorker(
        sync: TaskSyncScheduler.SyncType.UpdateTaskSync
    ) {
        val userId = authInfoStorage.get()?.userId ?: return
        val taskSyncEntity = TaskSyncEntity(
            taskId = sync.task.id,
            task = sync.task.toTaskEntity(),
            userId = userId,
            syncType = SyncType.UPDATE
        )

        val preScheduledTaskSyncEntity = taskSyncDao.getTaskPendingSyncById(
            taskId = sync.task.id,
            userId = userId,
            syncType = SyncType.CREATE
        )
        if (preScheduledTaskSyncEntity == null) {
            // It means it is already created in remoted
            taskSyncDao.upsertTaskPendingSync(taskSyncEntity)
            val workRequest = OneTimeWorkRequestBuilder<UpdateTaskWorker>()
                .addTag("$UPDATE_TASK${taskSyncEntity.taskId}")
                .addTag(TASK_WORK)
                .setRequiredNetworkConnectivity()
                .setExponentialBackOffPolicy(2000)
                .setInputParameters {
                    putString(
                        UpdateTaskWorker.TASK_ID,
                        taskSyncEntity.taskId
                    )
                }
                .build()

            applicationScope.launch {
                workManager.enqueue(workRequest).await()
            }.join()
        } else {
            // It means that it has not been created in remote yet so instead of update i will create
            workManager.cancelAllWorkByTag("$CREATE_TASK${taskSyncEntity.taskId}")
                .await() // cancel the previous work otherwise it will create again and throw error
            taskSyncDao.upsertTaskPendingSync(taskSyncEntity)
            val workRequest = OneTimeWorkRequestBuilder<CreateTaskWorker>()
                .addTag("$CREATE_TASK${taskSyncEntity.taskId}")
                .addTag(TASK_WORK)
                .setRequiredNetworkConnectivity()
                .setExponentialBackOffPolicy(2000)
                .setInputParameters {
                    putString(
                        CreateTaskWorker.TASK_ID,
                        taskSyncEntity.taskId
                    )
                }
                .build()

            applicationScope.launch {
                workManager.enqueue(workRequest).await()
            }.join()
        }
    }

    private suspend fun scheduleDeleteTaskWorker(
        sync: TaskSyncScheduler.SyncType.DeleteTaskSync
    ) {

        val userId = authInfoStorage.get()?.userId ?: return
        val taskDeleteSyncEntity = TaskDeleteSyncEntity(
            taskId = sync.taskId,
            userId = userId,
        )

        val preScheduledCreateTaskSyncEntity = taskSyncDao.getTaskPendingSyncById(
            taskId = sync.taskId,
            userId = userId,
            syncType = SyncType.CREATE
        ) // checking whether it has been created locally or not

        if (preScheduledCreateTaskSyncEntity == null) {

            val preScheduledUpdateTaskSyncEntity = taskSyncDao.getTaskPendingSyncById(
                taskId = sync.taskId,
                userId = userId,
                syncType = SyncType.UPDATE
            ) // checking whether there is an update scheduled for this task if it is i will cancel it
            if (preScheduledUpdateTaskSyncEntity != null) {
                workManager.cancelAllWorkByTag("$UPDATE_TASK${sync.taskId}")
                    .await()
                taskSyncDao.deleteTaskPendingSyncById(
                    taskId = sync.taskId,
                    userId = userId,
                    syncType = SyncType.UPDATE
                )
            }

            val workRequest = OneTimeWorkRequestBuilder<DeleteTaskWorker>()
                .addTag("$DELETE_TASK${taskDeleteSyncEntity.taskId}")
                .addTag(TASK_WORK)
                .setRequiredNetworkConnectivity()
                .setExponentialBackOffPolicy(2000)
                .setInputParameters {
                    putString(
                        DeleteTaskWorker.TASK_ID,
                        taskDeleteSyncEntity.taskId
                    )
                }
                .build()

            applicationScope.launch {
                workManager.enqueue(workRequest).await()
            }.join()

        } else {
            taskSyncDao.deleteTaskPendingSyncById(
                taskId = sync.taskId,
                userId = userId,
                syncType = SyncType.CREATE
            )
        }

    }

    companion object {
        const val CREATE_TASK = "create_task"
        const val UPDATE_TASK = "update_task"
        const val DELETE_TASK = "delete_task"
        const val TASK_WORK = "task_work"
    }

}