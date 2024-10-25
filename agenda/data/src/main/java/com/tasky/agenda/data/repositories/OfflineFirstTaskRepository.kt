package com.tasky.agenda.data.repositories

import com.tasky.agenda.domain.model.Task
import com.tasky.agenda.domain.repository.TaskRepository
import com.tasky.agenda.domain.data_sources.local.LocalTaskDataSource
import com.tasky.agenda.domain.data_sources.remote.RemoteTaskDataSource
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import com.tasky.core.domain.util.Result
import com.tasky.core.domain.util.asEmptyDataResult
import kotlinx.coroutines.flow.Flow

class OfflineFirstTaskRepository(
    private val localTaskDataSource: LocalTaskDataSource,
    private val remoteTaskDataSource: RemoteTaskDataSource
) : TaskRepository {


    override suspend fun addTask(task: Task): EmptyDataResult<DataError> {
        val localTaskResult = localTaskDataSource.upsertTask(task)
        if (localTaskResult !is Result.Success) {
            return localTaskResult.asEmptyDataResult()
        }
        return when (val remoteTaskResult = remoteTaskDataSource.create(task)) {
            is Result.Error -> {
                // @todo - i need to store that it has been yet created in remote data source
                Result.Error(remoteTaskResult.error)
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
                // @todo - i need to store that as it has been yet updated in remote data source
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

        // @todo - I need to check whether it was created remotely or not
        remoteTaskDataSource.delete(taskId)
    }

    override suspend fun getTaskById(taskId: String): Task? {
        return localTaskDataSource.getTaskById(taskId)
    }
}