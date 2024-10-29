package com.tasky.agenda.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "taskPendingSyncs"
)
data class TaskSyncEntity(
    @PrimaryKey val taskId:String,
    @Embedded val task: TaskEntity,
    val syncType: SyncType,
    val userId: String
)