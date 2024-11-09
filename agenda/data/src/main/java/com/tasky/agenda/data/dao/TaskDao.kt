package com.tasky.agenda.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.tasky.agenda.data.model.TaskEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {

    @Query("SELECT * FROM taskEntity WHERE id=:taskId")
    suspend fun getTaskById(taskId: String): TaskEntity?

    @Query("SELECT * FROM taskEntity")
    fun getTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM taskEntity WHERE time BETWEEN :startTime and :endTime")
    fun getTasksByTime(startTime: Long, endTime: Long): Flow<List<TaskEntity>>

    @Upsert
    suspend fun upsertTask(taskEntity: TaskEntity)

    @Upsert
    suspend fun upsertTasks(taskEntities: List<TaskEntity>)

    @Query("DELETE FROM taskEntity WHERE id=:taskId")
    suspend fun deleteTask(taskId: String)

    @Query("DELETE FROM taskEntity")
    suspend fun deleteAllTasks()

    @Query("SELECT * FROM taskEntity WHERE `time` >= :time")
    suspend fun getAllTasksGraterThanTime(time: Long): List<TaskEntity>

}