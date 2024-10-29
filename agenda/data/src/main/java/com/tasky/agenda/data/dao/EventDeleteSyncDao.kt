package com.tasky.agenda.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.tasky.agenda.data.model.EventDeleteSyncEntity
import com.tasky.agenda.data.model.EventSyncEntity

@Dao
interface EventDeleteSyncDao {

    @Query("SELECT * FROM eventDeletePendingSyncs")
    suspend fun getAllEventDeletePendingSyncs(): List<EventDeleteSyncEntity>

    @Query("DELETE FROM eventDeletePendingSyncs")
    suspend fun deleteAllEventDeletePendingSync()

    @Query("SELECT * FROM eventDeletePendingSyncs WHERE eventId=:eventId")
    suspend fun getEventDeletePendingSyncById(eventId: String): EventSyncEntity?

    @Query("DELETE FROM eventDeletePendingSyncs WHERE eventId=:eventId")
    suspend fun deleteEventDeletePendingSyncById(eventId: String)

    @Upsert
    suspend fun upsertEventDeletePendingSync(eventDeleteSyncEntity: EventDeleteSyncEntity)

    @Upsert
    suspend fun upsertEventDeletePendingSyncs(eventDeleteSyncEntities: List<EventDeleteSyncEntity>)

}