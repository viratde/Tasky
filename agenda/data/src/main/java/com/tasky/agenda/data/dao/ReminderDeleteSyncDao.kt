package com.tasky.agenda.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.tasky.agenda.data.model.ReminderDeleteSyncEntity

@Dao
interface ReminderDeleteSyncDao {

    @Query("SELECT * FROM reminderDeletePendingSyncs WHERE userId=:userId")
    suspend fun getAllReminderDeletePendingSyncs(userId: String): List<ReminderDeleteSyncEntity>

    @Query("DELETE FROM reminderDeletePendingSyncs WHERE userId=:userId")
    suspend fun deleteAllReminderDeletePendingSync(userId: String)

    @Query("SELECT * FROM reminderDeletePendingSyncs WHERE reminderId=:reminderId AND userId=:userId")
    suspend fun getReminderDeletePendingSyncById(
        reminderId: String,
        userId: String
    ): ReminderDeleteSyncEntity?

    @Query("DELETE FROM reminderDeletePendingSyncs WHERE reminderId=:reminderId AND userId=:userId")
    suspend fun deleteReminderDeletePendingSyncById(reminderId: String, userId: String)

    @Upsert
    suspend fun upsertReminderDeletePendingSync(reminderDeleteSyncEntity: ReminderDeleteSyncEntity)

    @Upsert
    suspend fun upsertReminderDeletePendingSyncs(reminderDeleteSyncEntities: List<ReminderDeleteSyncEntity>)

}