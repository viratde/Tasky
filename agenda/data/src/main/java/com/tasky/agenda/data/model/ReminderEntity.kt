package com.tasky.agenda.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "reminderEntity",
)
data class ReminderEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val remindAt: Long,
    val time: Long
)
