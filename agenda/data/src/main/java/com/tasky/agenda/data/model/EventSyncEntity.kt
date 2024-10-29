package com.tasky.agenda.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "eventPendingSyncs"
)
data class EventSyncEntity(
    @PrimaryKey val eventId: String,
    @Embedded val event: EventEntity,
    val syncType: SyncType,
    val userId: String
)