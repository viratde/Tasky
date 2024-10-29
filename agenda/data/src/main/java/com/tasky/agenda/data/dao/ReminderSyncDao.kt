package com.tasky.agenda.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.tasky.agenda.data.model.ReminderSyncEntity
import com.tasky.agenda.data.model.SyncType

@Dao
interface ReminderSyncDao {

    @Query("SELECT * FROM reminderPendingSyncs WHERE syncType=:syncType AND userId=:userId")
    suspend fun getAllReminderPendingSyncs(
        syncType: SyncType,
        userId: String
    ): List<ReminderSyncEntity>

    @Query("DELETE FROM reminderPendingSyncs WHERE syncType=:syncType AND userId=:userId")
    suspend fun deleteAllReminderPendingSync(syncType: SyncType, userId: String)

    @Query("SELECT * FROM reminderPendingSyncs WHERE reminderId=:reminderId AND syncType=:syncType AND userId=:userId")
    suspend fun getReminderPendingSyncById(
        reminderId: String,
        syncType: SyncType,
        userId: String
    ): ReminderSyncEntity?

    @Query("DELETE FROM reminderPendingSyncs WHERE reminderId=:reminderId AND syncType=:syncType AND userId=:userId")
    suspend fun deleteReminderPendingSyncById(
        reminderId: String,
        syncType: SyncType,
        userId: String
    )

    @Upsert
    suspend fun upsertReminderPendingSync(reminderSyncEntity: ReminderSyncEntity)

    @Upsert
    suspend fun upsertReminderPendingSyncs(reminderSyncEntities: List<ReminderSyncEntity>)

}