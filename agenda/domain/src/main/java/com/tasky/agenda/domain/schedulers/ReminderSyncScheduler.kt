package com.tasky.agenda.domain.schedulers

import com.tasky.agenda.domain.model.Reminder

interface ReminderSyncScheduler {

    suspend fun sync(syncType: SyncType)

    suspend fun cancelAllSyncs()

    sealed interface SyncType {

        data class CreateReminderSync(
            val event: Reminder
        ) : SyncType

        data class UpdateReminderSync(
            val event: Reminder
        ) : SyncType

        data class DeleteReminderSync(
            val eventId: String
        ) : SyncType

    }
}