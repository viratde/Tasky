package com.tasky.agenda.data.workers.task

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.tasky.agenda.data.dao.TaskDeleteSyncDao
import com.tasky.agenda.domain.data_sources.remote.RemoteTaskDataSource
import com.tasky.core.data.utils.toWorkerResult
import com.tasky.core.domain.AuthInfoStorage
import com.tasky.core.domain.util.Result

class DeleteTaskWorker(
    context: Context,
    private val params: WorkerParameters,
    private val authInfoStorage: AuthInfoStorage,
    private val taskDeleteSyncDao: TaskDeleteSyncDao,
    private val remoteTaskDataSource: RemoteTaskDataSource
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        if (runAttemptCount > 5) {
            return Result.failure()
        }

        val taskId = params.inputData.getString(TASK_ID) ?: return Result.failure()
        val userId = authInfoStorage.get()?.userId ?: return Result.failure()
        val deleteTaskSyncEntity = taskDeleteSyncDao.getTaskDeletePendingSyncById(
            taskId = taskId,
            userId = userId
        ) ?: return Result.failure()

        return when (val result = remoteTaskDataSource.delete(deleteTaskSyncEntity.taskId)) {
            is com.tasky.core.domain.util.Result.Error -> {
                result.error.toWorkerResult()
            }

            is com.tasky.core.domain.util.Result.Success -> {
                taskDeleteSyncDao.deleteTaskDeletePendingSyncById(
                    taskId = taskId,
                    userId = userId,
                )
                Result.success()
            }
        }

    }

    companion object {
        const val TASK_ID = "task_id"
        const val TAG = "delete_task_work"
    }
}