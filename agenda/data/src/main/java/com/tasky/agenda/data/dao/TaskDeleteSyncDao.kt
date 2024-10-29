package com.tasky.agenda.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.tasky.agenda.data.model.TaskDeleteSyncEntity
import com.tasky.agenda.data.model.TaskSyncEntity

@Dao
interface TaskDeleteSyncDao {

    @Query("SELECT * FROM taskDeletePendingSyncs WHERE userId=:userId")
    suspend fun getAllTaskDeletePendingSyncs(userId: String): List<TaskDeleteSyncEntity>

    @Query("DELETE FROM taskDeletePendingSyncs WHERE userId=:userId")
    suspend fun deleteAllTaskDeletePendingSync(userId: String)

    @Query("SELECT * FROM taskDeletePendingSyncs WHERE taskId=:taskId AND userId=:userId")
    suspend fun getTaskDeletePendingSyncById(taskId: String, userId: String): TaskSyncEntity?

    @Query("DELETE FROM taskDeletePendingSyncs WHERE taskId=:taskId AND userId=:userId")
    suspend fun deleteTaskDeletePendingSyncById(taskId: String, userId: String)

    @Upsert
    suspend fun upsertTaskDeletePendingSync(taskDeleteSyncEntity: TaskDeleteSyncEntity)

    @Upsert
    suspend fun upsertTaskDeletePendingSyncs(taskDeleteSyncEntities: List<TaskDeleteSyncEntity>)

}