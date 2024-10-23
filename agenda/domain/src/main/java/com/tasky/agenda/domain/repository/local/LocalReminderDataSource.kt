package com.tasky.agenda.domain.repository.local

import com.tasky.agenda.domain.model.Reminder
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import kotlinx.coroutines.flow.Flow

interface LocalReminderDataSource {

    suspend fun getAgendaItemsById(agendaItemId: String): Reminder?

    suspend fun getAgendaItemsByTime(time: Long): Flow<List<Reminder>>

    suspend fun upsertAgendaItem(agendaItem: Reminder): EmptyDataResult<DataError.Local>

    suspend fun upsertAgendaItems(agendaItems: List<Reminder>): EmptyDataResult<DataError.Local>

    suspend fun deleteAgendaItem(agendaItemId: String)

    suspend fun getAgendaItems(): Flow<List<Reminder>>

    suspend fun deleteAllAgendaItems()

}