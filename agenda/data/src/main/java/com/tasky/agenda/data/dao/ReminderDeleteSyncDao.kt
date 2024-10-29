package com.tasky.agenda.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.tasky.agenda.data.model.ReminderDeleteSyncEntity

@Dao
interface ReminderDeleteSyncDao {

    @Query("SELECT * FROM reminderDeletePendingSyncs")
    suspend fun getAllReminderDeletePendingSyncs(): List<ReminderDeleteSyncEntity>

    @Query("DELETE FROM reminderDeletePendingSyncs")
    suspend fun deleteAllReminderDeletePendingSync()

    @Query("SELECT * FROM reminderDeletePendingSyncs WHERE reminderId=:reminderId")
    suspend fun getReminderDeletePendingSyncById(reminderId: String): ReminderDeleteSyncEntity?

    @Query("DELETE FROM reminderDeletePendingSyncs WHERE reminderId=:reminderId")
    suspend fun deleteReminderDeletePendingSyncById(reminderId: String)

    @Upsert
    suspend fun upsertReminderDeletePendingSync(reminderDeleteSyncEntity: ReminderDeleteSyncEntity)

    @Upsert
    suspend fun upsertReminderDeletePendingSyncs(reminderDeleteSyncEntities: List<ReminderDeleteSyncEntity>)

}