package com.tasky.agenda.domain.data_sources.local

import com.tasky.agenda.domain.model.Task
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import kotlinx.coroutines.flow.Flow

interface LocalTaskDataSource {

    suspend fun getTasksById(agendaItemId: String): Task?

    fun getTasksByTime(time: Long): Flow<List<Task>>

    suspend fun upsertTask(agendaItem: Task): EmptyDataResult<DataError.Local>

    suspend fun upsertTasks(agendaItems: List<Task>): EmptyDataResult<DataError.Local>

    suspend fun deleteTask(agendaItemId: String)

    fun getTasks(): Flow<List<Task>>

    suspend fun deleteAllTasks()

}