package com.tasky.agenda.domain.repository.local

import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import kotlinx.coroutines.flow.Flow


interface LocalAgendaRepository<T> {

    suspend fun getAgendaItemsById(agendaItemId: String): T?

    suspend fun getAgendaItemsByTime(time: Long): Flow<List<T>>

    suspend fun upsertAgendaItem(agendaItem: T): EmptyDataResult<DataError.Local>

    suspend fun upsertAgendaItems(agendaItems: List<T>): EmptyDataResult<DataError.Local>

    suspend fun deleteAgendaItem(agendaItemId: String)

    suspend fun getAgendaItems(): Flow<List<T>>

    suspend fun deleteAllAgendaItems()

}