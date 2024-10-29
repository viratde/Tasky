package com.tasky.agenda.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.tasky.agenda.data.model.SyncType
import com.tasky.agenda.data.model.TaskSyncEntity

@Dao
interface TaskSyncDao {

    @Query("SELECT * FROM taskPendingSyncs WHERE syncType=:syncType")
    suspend fun getAllTaskPendingSyncs(syncType: SyncType): List<TaskSyncEntity>

    @Query("DELETE FROM taskPendingSyncs WHERE syncType=:syncType")
    suspend fun deleteAllTaskPendingSync(syncType: SyncType)

    @Query("SELECT * FROM taskPendingSyncs WHERE taskId=:taskId AND syncType=:syncType")
    suspend fun getTaskPendingSyncById(taskId: String, syncType: SyncType): TaskSyncEntity?

    @Query("DELETE FROM taskPendingSyncs WHERE taskId=:taskId AND syncType=:syncType")
    suspend fun deleteTaskPendingSyncById(taskId: String, syncType: SyncType)

    @Upsert
    suspend fun upsertTaskPendingSync(taskSyncEntity: TaskSyncEntity)

    @Upsert
    suspend fun upsertTaskPendingSyncs(taskSyncEntities: List<TaskSyncEntity>)

}