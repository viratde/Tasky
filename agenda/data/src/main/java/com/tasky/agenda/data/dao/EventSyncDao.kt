package com.tasky.agenda.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.tasky.agenda.data.model.EventSyncEntity
import com.tasky.agenda.data.model.SyncType

@Dao
interface EventSyncDao {

    @Query("SELECT * FROM eventPendingSyncs WHERE syncType=:syncType AND userId=:userId")
    suspend fun getAllEventPendingSyncs(syncType: SyncType, userId: String): List<EventSyncEntity>

    @Query("DELETE FROM eventPendingSyncs WHERE syncType=:syncType AND userId=:userId")
    suspend fun deleteAllEventPendingSync(syncType: SyncType, userId: String)

    @Query("SELECT * FROM eventPendingSyncs WHERE eventId=:eventId AND syncType=:syncType AND userId=:userId")
    suspend fun getEventPendingSyncById(
        eventId: String,
        syncType: SyncType,
        userId: String
    ): EventSyncEntity?

    @Query("DELETE FROM eventPendingSyncs WHERE eventId=:eventId AND syncType=:syncType AND userId=:userId")
    suspend fun deleteEventPendingSyncById(eventId: String, syncType: SyncType, userId: String)

    @Upsert
    suspend fun upsertEventPendingSync(eventSyncEntity: EventSyncEntity)

    @Upsert
    suspend fun upsertEventPendingSyncs(eventSyncEntities: List<EventSyncEntity>)

}