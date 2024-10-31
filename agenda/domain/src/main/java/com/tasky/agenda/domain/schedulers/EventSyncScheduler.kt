package com.tasky.agenda.domain.schedulers

import com.tasky.agenda.domain.model.Event

interface EventSyncScheduler {

    suspend fun sync(syncType: SyncType)

    suspend fun cancelAllSyncs()

    sealed interface SyncType {

        data class CreateEventSync(
            val event: Event
        ) : SyncType

        data class UpdateEventSync(
            val event: Event
        ) : SyncType

        data class DeleteEventSync(
            val eventId: String
        ) : SyncType

    }
}