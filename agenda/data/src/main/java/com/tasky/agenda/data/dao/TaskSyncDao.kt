package com.tasky.agenda.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.tasky.agenda.data.model.SyncType
import com.tasky.agenda.data.model.TaskSyncEntity

@Dao
interface TaskSyncDao {

    @Query("SELECT * FROM taskPendingSyncs WHERE syncType=:syncType AND userId=:userId")
    suspend fun getAllTaskPendingSyncs(syncType: SyncType, userId: String): List<TaskSyncEntity>

    @Query("DELETE FROM taskPendingSyncs WHERE syncType=:syncType AND userId=:userId")
    suspend fun deleteAllTaskPendingSync(syncType: SyncType, userId: String)

    @Query("SELECT * FROM taskPendingSyncs WHERE taskId=:taskId AND syncType=:syncType AND userId=:userId")
    suspend fun getTaskPendingSyncById(
        taskId: String,
        syncType: SyncType,
        userId: String
    ): TaskSyncEntity?

    @Query("DELETE FROM taskPendingSyncs WHERE taskId=:taskId AND syncType=:syncType AND userId=:userId")
    suspend fun deleteTaskPendingSyncById(taskId: String, syncType: SyncType, userId: String)

    @Upsert
    suspend fun upsertTaskPendingSync(taskSyncEntity: TaskSyncEntity)

    @Upsert
    suspend fun upsertTaskPendingSyncs(taskSyncEntities: List<TaskSyncEntity>)

}