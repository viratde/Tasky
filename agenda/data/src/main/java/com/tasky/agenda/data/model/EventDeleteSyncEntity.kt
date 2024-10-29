package com.tasky.agenda.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "eventDeletePendingSyncs"
)
data class EventDeleteSyncEntity(
    @PrimaryKey val eventId:String,
    val userId:String
)
