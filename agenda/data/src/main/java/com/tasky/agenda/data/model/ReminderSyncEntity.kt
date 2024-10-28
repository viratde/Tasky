package com.tasky.agenda.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "reminderPendingSyncs"
)
data class ReminderSyncEntity(
    @PrimaryKey val reminderId: String,
    @Embedded val reminder: ReminderEntity,
    val syncType: SyncType,
    val userId: String
)