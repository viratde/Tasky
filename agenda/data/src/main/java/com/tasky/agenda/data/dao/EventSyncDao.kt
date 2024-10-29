package com.tasky.agenda.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.tasky.agenda.data.model.EventSyncEntity
import com.tasky.agenda.data.model.SyncType

@Dao
interface EventSyncDao {

    @Query("SELECT * FROM eventPendingSyncs WHERE syncType=:syncType")
    suspend fun getAllEventPendingSyncs(syncType: SyncType): List<EventSyncEntity>

    @Query("DELETE FROM eventPendingSyncs WHERE syncType=:syncType")
    suspend fun deleteAllEventPendingSync(syncType: SyncType)

    @Query("SELECT * FROM eventPendingSyncs WHERE eventId=:eventId AND syncType=:syncType")
    suspend fun getEventPendingSyncById(eventId: String, syncType: SyncType): EventSyncEntity?

    @Query("DELETE FROM eventPendingSyncs WHERE eventId=:eventId AND syncType=:syncType")
    suspend fun deleteEventPendingSyncById(eventId: String, syncType: SyncType)

    @Upsert
    suspend fun upsertEventPendingSync(eventSyncEntity: EventSyncEntity)

    @Upsert
    suspend fun upsertEventPendingSyncs(eventSyncEntities: List<EventSyncEntity>)

}