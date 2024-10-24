package com.tasky.agenda.domain.repository

import com.tasky.agenda.domain.model.Task
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun addTask(task: Task): EmptyDataResult<DataError>

    suspend fun updateTask(task: Task): EmptyDataResult<DataError>

    suspend fun getTasksByTime(time: Long): Flow<List<Task>>

    suspend fun deleteTaskById(taskId: String)

}