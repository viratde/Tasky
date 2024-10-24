package com.tasky.agenda.domain.repository.local

import com.tasky.agenda.domain.model.Event
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import kotlinx.coroutines.flow.Flow

interface LocalEventDataSource {

    suspend fun getAgendaItemsById(agendaItemId: String): Event?

    suspend fun getAgendaItemsByTime(time: Long): Flow<List<Event>>

    suspend fun upsertAgendaItem(agendaItem: Event): EmptyDataResult<DataError.Local>

    suspend fun upsertAgendaItems(agendaItems: List<Event>): EmptyDataResult<DataError.Local>

    suspend fun deleteAgendaItem(agendaItemId: String)

    suspend fun getAgendaItems(): Flow<List<Event>>

    suspend fun deleteAllAgendaItems()

}