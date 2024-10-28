package com.tasky.agenda.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "reminderDeleteSyncs"
)
data class ReminderDeleteSyncEntity(
    @PrimaryKey val reminderId: String,
    val userId: String
)