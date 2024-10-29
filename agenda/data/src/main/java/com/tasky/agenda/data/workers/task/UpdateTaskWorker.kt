package com.tasky.agenda.data.workers.task

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.tasky.agenda.data.dao.TaskDeleteSyncDao
import com.tasky.agenda.data.dao.TaskSyncDao
import com.tasky.agenda.data.mappers.toTask
import com.tasky.agenda.data.model.SyncType
import com.tasky.agenda.domain.data_sources.remote.RemoteTaskDataSource
import com.tasky.core.data.utils.toWorkerResult
import com.tasky.core.domain.AuthInfoStorage
import com.tasky.core.domain.util.Result
import kotlinx.coroutines.CoroutineScope

class UpdateTaskWorker(
    context: Context,
    private val params: WorkerParameters,
    private val authInfoStorage: AuthInfoStorage,
    private val taskSyncDao: TaskSyncDao,
    private val remoteTaskDataSource: RemoteTaskDataSource
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        if (runAttemptCount > 5) {
            return Result.failure()
        }

        val taskId = params.inputData.getString(TASK_ID) ?: return Result.failure()
        val userId = authInfoStorage.get()?.userId ?: return Result.failure()
        val updateTaskSyncEntity = taskSyncDao.getTaskPendingSyncById(
            taskId = taskId,
            userId = userId,
            syncType = SyncType.UPDATE
        ) ?: return Result.failure()


        return when (val result = remoteTaskDataSource.update(updateTaskSyncEntity.task.toTask())) {
            is com.tasky.core.domain.util.Result.Error -> {
                result.error.toWorkerResult()
            }

            is com.tasky.core.domain.util.Result.Success -> {
                taskSyncDao.deleteTaskPendingSyncById(
                    taskId = taskId,
                    userId = userId,
                    syncType = SyncType.UPDATE
                )
                Result.success()
            }
        }

    }

    companion object {
        const val TASK_ID = "TASK_ID"
    }
}