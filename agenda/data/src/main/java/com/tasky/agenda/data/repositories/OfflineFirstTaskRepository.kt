package com.tasky.agenda.data.repositories

import com.tasky.agenda.domain.model.Task
import com.tasky.agenda.domain.repository.TaskRepository
import com.tasky.agenda.domain.data_sources.local.LocalTaskDataSource
import com.tasky.agenda.domain.data_sources.remote.RemoteTaskDataSource
import com.tasky.agenda.domain.schedulers.TaskSyncScheduler
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import com.tasky.core.domain.util.Result
import com.tasky.core.domain.util.asEmptyDataResult
import kotlinx.coroutines.flow.Flow

class OfflineFirstTaskRepository(
    private val localTaskDataSource: LocalTaskDataSource,
    private val remoteTaskDataSource: RemoteTaskDataSource,
    private val taskSyncScheduler: TaskSyncScheduler
) : TaskRepository {


    override suspend fun addTask(task: Task): EmptyDataResult<DataError> {
        val localTaskResult = localTaskDataSource.upsertTask(task)
        if (localTaskResult !is Result.Success) {
            return localTaskResult.asEmptyDataResult()
        }
        return when (val remoteTaskResult = remoteTaskDataSource.create(task)) {
            is Result.Error -> {
                taskSyncScheduler.sync(TaskSyncScheduler.SyncType.CreateTaskSync(task))
                Result.Success(Unit)
            }

            is Result.Success -> {
                remoteTaskResult.asEmptyDataResult()
            }
        }
    }

    override suspend fun updateTask(task: Task): EmptyDataResult<DataError> {
        val localTaskResult = localTaskDataSource.upsertTask(task)
        if (localTaskResult !is Result.Success) {
            return localTaskResult.asEmptyDataResult()
        }
        return when (val remoteTaskResult =
            remoteTaskDataSource.update(task)) {
            is Result.Error -> {
                taskSyncScheduler.sync(TaskSyncScheduler.SyncType.UpdateTaskSync(task))
                Result.Success(Unit)
            }

            is Result.Success -> {
                remoteTaskResult.asEmptyDataResult()
            }
        }
    }

    override suspend fun getTasksByTime(time: Long): Flow<List<Task>> {
        return localTaskDataSource.getTasksByTime(time)
    }

    override suspend fun deleteTaskById(taskId: String) {
        localTaskDataSource.deleteTask(taskId)
        val result = remoteTaskDataSource.delete(taskId)
        if (result is Result.Error) {
            taskSyncScheduler.sync(TaskSyncScheduler.SyncType.DeleteTaskSync(taskId))
        }
    }

    override suspend fun getTaskById(taskId: String): Task? {
        return localTaskDataSource.getTaskById(taskId)
    }
}