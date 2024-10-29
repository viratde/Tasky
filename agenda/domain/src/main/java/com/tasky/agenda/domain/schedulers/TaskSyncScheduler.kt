package com.tasky.agenda.domain.schedulers

import com.tasky.agenda.domain.model.Task

interface TaskSyncScheduler {

    suspend fun sync(syncType: SyncType)

    suspend fun cancelAllSyncs()

    sealed interface SyncType {

        data class CreateTaskSync(
            val event: Task
        ) : SyncType

        data class UpdateTaskSync(
            val event: Task
        ) : SyncType

        data class DeleteTaskSync(
            val eventId: String
        ) : SyncType

        data class CancelCreateTaskSync(
            val eventId: String
        ) : SyncType

        data class CancelUpdateTaskSync(
            val eventId: String
        ) : SyncType

    }
}