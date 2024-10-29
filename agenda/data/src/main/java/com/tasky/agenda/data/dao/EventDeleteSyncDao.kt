package com.tasky.agenda.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.tasky.agenda.data.model.EventDeleteSyncEntity
import com.tasky.agenda.data.model.EventSyncEntity

@Dao
interface EventDeleteSyncDao {

    @Query("SELECT * FROM eventDeletePendingSyncs WHERE userId=:userId")
    suspend fun getAllEventDeletePendingSyncs(userId: String): List<EventDeleteSyncEntity>

    @Query("DELETE FROM eventDeletePendingSyncs WHERE userId=:userId")
    suspend fun deleteAllEventDeletePendingSync(userId: String)

    @Query("SELECT * FROM eventDeletePendingSyncs WHERE eventId=:eventId AND userId=:userId")
    suspend fun getEventDeletePendingSyncById(eventId: String, userId: String): EventDeleteSyncEntity?

    @Query("DELETE FROM eventDeletePendingSyncs WHERE eventId=:eventId AND userId=:userId")
    suspend fun deleteEventDeletePendingSyncById(eventId: String, userId: String)

    @Upsert
    suspend fun upsertEventDeletePendingSync(eventDeleteSyncEntity: EventDeleteSyncEntity)

    @Upsert
    suspend fun upsertEventDeletePendingSyncs(eventDeleteSyncEntities: List<EventDeleteSyncEntity>)

}