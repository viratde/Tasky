package com.tasky.agenda.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "taskEntity"
)
data class TaskEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val remindAt: Long,
    val time: Long,
    val isDone: Boolean
)
