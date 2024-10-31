package com.tasky.agenda.domain.schedulers

import com.tasky.agenda.domain.model.Task

interface TaskSyncScheduler {

    suspend fun sync(syncType: SyncType)

    suspend fun cancelAllSyncs()

    sealed interface SyncType {

        data class CreateTaskSync(
            val task: Task
        ) : SyncType

        data class UpdateTaskSync(
            val task: Task
        ) : SyncType

        data class DeleteTaskSync(
            val taskId: String
        ) : SyncType

    }
}