package com.tasky.agenda.data.data_sources.local

import android.database.sqlite.SQLiteFullException
import com.tasky.agenda.data.dao.TaskDao
import com.tasky.agenda.data.mappers.toTask
import com.tasky.agenda.data.mappers.toTaskEntity
import com.tasky.agenda.domain.model.Task
import com.tasky.agenda.domain.data_sources.local.LocalTaskDataSource
import com.tasky.core.data.utils.getEndOfDay
import com.tasky.core.data.utils.getStartOfDay
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import com.tasky.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomTaskLocalDataSource(
    private val taskDao: TaskDao
) : LocalTaskDataSource {

    override suspend fun getTaskById(agendaItemId: String): Task? {
        return taskDao.getTaskById(taskId = agendaItemId)?.toTask()
    }

    override fun getTasksByTime(time: Long): Flow<List<Task>> {
        return taskDao.getTasksByTime(time.getStartOfDay(), time.getEndOfDay())
            .map { taskEntities -> taskEntities.map { it.toTask() } }
    }

    override suspend fun upsertTask(agendaItem: Task): EmptyDataResult<DataError.Local> {
        return try {
            taskDao.upsertTask(agendaItem.toTaskEntity())
            Result.Success(Unit)
        } catch (err: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun upsertTasks(agendaItems: List<Task>): EmptyDataResult<DataError.Local> {
        return try {
            taskDao.upsertTasks(agendaItems.map { it.toTaskEntity() })
            Result.Success(Unit)
        } catch (err: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteTask(agendaItemId: String) {
        taskDao.deleteTask(agendaItemId)
    }

    override fun getTasks(): Flow<List<Task>> {
        return taskDao.getTasks().map { taskEntities -> taskEntities.map { it.toTask() } }
    }

    override suspend fun deleteAllTasks() {
        return taskDao.deleteAllTasks()
    }
}