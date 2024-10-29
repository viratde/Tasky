package com.tasky.agenda.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.tasky.agenda.data.model.ReminderSyncEntity
import com.tasky.agenda.data.model.SyncType

@Dao
interface ReminderSyncDao {

    @Query("SELECT * FROM reminderPendingSyncs WHERE syncType=:syncType")
    suspend fun getAllReminderPendingSyncs(syncType: SyncType): List<ReminderSyncEntity>

    @Query("DELETE FROM reminderPendingSyncs WHERE syncType=:syncType")
    suspend fun deleteAllReminderPendingSync(syncType: SyncType)

    @Query("SELECT * FROM reminderPendingSyncs WHERE reminderId=:reminderId AND syncType=:syncType")
    suspend fun getReminderPendingSyncById(reminderId: String, syncType: SyncType): ReminderSyncEntity?

    @Query("DELETE FROM reminderPendingSyncs WHERE reminderId=:reminderId AND syncType=:syncType")
    suspend fun deleteReminderPendingSyncById(reminderId: String, syncType: SyncType)

    @Upsert
    suspend fun upsertReminderPendingSync(reminderSyncEntity: ReminderSyncEntity)

    @Upsert
    suspend fun upsertReminderPendingSyncs(reminderSyncEntities: List<ReminderSyncEntity>)

}