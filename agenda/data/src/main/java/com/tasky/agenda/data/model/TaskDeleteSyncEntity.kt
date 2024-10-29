package com.tasky.agenda.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "taskDeletePendingSyncs"
)
data class TaskDeleteSyncEntity(
    @PrimaryKey val taskId:String,
    val userId:String
)
