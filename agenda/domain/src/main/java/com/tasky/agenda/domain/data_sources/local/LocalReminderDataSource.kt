package com.tasky.agenda.domain.data_sources.local

import com.tasky.agenda.domain.model.Reminder
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import kotlinx.coroutines.flow.Flow

interface LocalReminderDataSource {

    suspend fun getRemindersById(agendaItemId: String): Reminder?

    fun getRemindersByTime(time: Long): Flow<List<Reminder>>

    suspend fun upsertReminder(agendaItem: Reminder): EmptyDataResult<DataError.Local>

    suspend fun upsertReminders(agendaItems: List<Reminder>): EmptyDataResult<DataError.Local>

    suspend fun deleteReminder(agendaItemId: String)

    fun getReminders(): Flow<List<Reminder>>

    suspend fun deleteAllReminders()

}