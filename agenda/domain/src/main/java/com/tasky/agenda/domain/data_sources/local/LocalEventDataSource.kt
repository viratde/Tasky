package com.tasky.agenda.domain.data_sources.local

import com.tasky.agenda.domain.model.Event
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import kotlinx.coroutines.flow.Flow

interface LocalEventDataSource {

    suspend fun getEventById(agendaItemId: String): Event?

    fun getEventsByTime(time: Long): Flow<List<Event>>

    suspend fun upsertEvent(agendaItem: Event): EmptyDataResult<DataError.Local>

    suspend fun upsertEvents(agendaItems: List<Event>): EmptyDataResult<DataError.Local>

    suspend fun deleteEvent(agendaItemId: String)

    fun getEvents(): Flow<List<Event>>

    suspend fun deleteAllEvents()

}