package com.tasky.agenda.data.schedulers

import android.content.Context
import androidx.work.WorkManager
import androidx.work.await
import com.tasky.agenda.data.dao.TaskDeleteSyncDao
import com.tasky.agenda.data.dao.TaskSyncDao
import com.tasky.agenda.data.mappers.toTaskEntity
import com.tasky.agenda.data.model.SyncType
import com.tasky.agenda.data.model.TaskDeleteSyncEntity
import com.tasky.agenda.data.model.TaskSyncEntity
import com.tasky.agenda.data.schedulers.util.WorkerType
import com.tasky.agenda.data.schedulers.util.toTaskOneTimeWorkRequest
import com.tasky.agenda.domain.schedulers.TaskSyncScheduler
import com.tasky.core.domain.AuthInfoStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TaskWorkSyncScheduler(
    context: Context,
    private val authInfoStorage: AuthInfoStorage,
    private val taskSyncDao: TaskSyncDao,
    private val taskDeleteSyncDao: TaskDeleteSyncDao,
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
        applicationScope.launch {
            workManager.enqueue(WorkerType.CREATE.toTaskOneTimeWorkRequest(sync.task.id)).await()
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
        val pendingCreateTaskSync = taskSyncDao.getTaskPendingSyncById(
            taskId = sync.task.id,
            userId = userId,
            syncType = SyncType.CREATE
        )
        applicationScope.launch {
            if (pendingCreateTaskSync != null) {
                workManager.cancelAllWorkByTag("$CREATE_TASK${pendingCreateTaskSync.taskId}")
                    .await()
                taskSyncDao.upsertTaskPendingSync(
                    taskSyncEntity.copy(
                        syncType = SyncType.CREATE
                    )
                )
                workManager.enqueue(WorkerType.CREATE.toTaskOneTimeWorkRequest(sync.task.id))
                    .await()
            } else {
                taskSyncDao.upsertTaskPendingSync(taskSyncEntity)
                workManager.enqueue(WorkerType.UPDATE.toTaskOneTimeWorkRequest(sync.task.id))
                    .await()
            }
        }.join()
    }

    private suspend fun scheduleDeleteTaskWorker(
        sync: TaskSyncScheduler.SyncType.DeleteTaskSync
    ) {
        val userId = authInfoStorage.get()?.userId ?: return
        val taskDeleteSyncEntity = TaskDeleteSyncEntity(
            taskId = sync.taskId,
            userId = userId,
        )
        val pendingCreateTaskSync = taskSyncDao.getTaskPendingSyncById(
            taskId = sync.taskId,
            userId = userId,
            syncType = SyncType.CREATE
        )
        val pendingUpdateTaskSync = taskSyncDao.getTaskPendingSyncById(
            taskId = sync.taskId,
            userId = userId,
            syncType = SyncType.UPDATE
        )
        applicationScope.launch {
            workManager.apply {
                if (pendingCreateTaskSync != null) {
                    cancelAllWorkByTag("$CREATE_TASK${pendingCreateTaskSync.taskId}").await()
                    taskSyncDao.deleteTaskPendingSyncById(
                        taskId = sync.taskId,
                        userId = userId,
                        syncType = SyncType.CREATE
                    )
                }
                if (pendingUpdateTaskSync != null) {
                    cancelAllWorkByTag("$UPDATE_TASK${pendingUpdateTaskSync.taskId}").await()
                    taskSyncDao.deleteTaskPendingSyncById(
                        taskId = sync.taskId,
                        userId = userId,
                        syncType = SyncType.UPDATE
                    )
                }
                if (pendingCreateTaskSync == null) {
                    taskDeleteSyncDao.upsertTaskDeletePendingSync(taskDeleteSyncEntity)
                    enqueue(WorkerType.DELETE.toTaskOneTimeWorkRequest(taskDeleteSyncEntity.taskId))
                        .await()
                }
            }
        }.join()
    }

    companion object {
        const val CREATE_TASK = "create_task"
        const val UPDATE_TASK = "update_task"
        const val DELETE_TASK = "delete_task"
        const val TASK_WORK = "task_work"
    }

}