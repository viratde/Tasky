package com.tasky.agenda.domain.schedulers

import com.tasky.agenda.domain.model.Reminder

interface ReminderSyncScheduler {

    suspend fun sync(syncType: SyncType)

    suspend fun cancelAllSyncs()

    sealed interface SyncType {

        data class CreateReminderSync(
            val reminder: Reminder
        ) : SyncType

        data class UpdateReminderSync(
            val reminder: Reminder
        ) : SyncType

        data class DeleteReminderSync(
            val reminderId: String
        ) : SyncType

    }
}