package com.tasky.agenda.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.tasky.agenda.data.model.TaskDeleteSyncEntity
import com.tasky.agenda.data.model.TaskSyncEntity

@Dao
interface TaskDeleteSyncDao {

    @Query("SELECT * FROM taskDeletePendingSyncs")
    suspend fun getAllTaskDeletePendingSyncs(): List<TaskDeleteSyncEntity>

    @Query("DELETE FROM taskDeletePendingSyncs")
    suspend fun deleteAllTaskDeletePendingSync()

    @Query("SELECT * FROM taskDeletePendingSyncs WHERE taskId=:taskId")
    suspend fun getTaskDeletePendingSyncById(taskId: String): TaskSyncEntity?

    @Query("DELETE FROM taskDeletePendingSyncs WHERE taskId=:taskId")
    suspend fun deleteTaskDeletePendingSyncById(taskId: String)

    @Upsert
    suspend fun upsertTaskDeletePendingSync(taskDeleteSyncEntity: TaskDeleteSyncEntity)

    @Upsert
    suspend fun upsertTaskDeletePendingSyncs(taskDeleteSyncEntities: List<TaskDeleteSyncEntity>)

}