package com.tasky.agenda.domain.repository.local

import com.tasky.agenda.domain.model.Task
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import kotlinx.coroutines.flow.Flow

interface LocalTaskDataSource {

    suspend fun getAgendaItemsById(agendaItemId: String): Task?

    suspend fun getAgendaItemsByTime(time: Long): Flow<List<Task>>

    suspend fun upsertAgendaItem(agendaItem: Task): EmptyDataResult<DataError.Local>

    suspend fun upsertAgendaItems(agendaItems: List<Task>): EmptyDataResult<DataError.Local>

    suspend fun deleteAgendaItem(agendaItemId: String)

    suspend fun getAgendaItems(): Flow<List<Task>>

    suspend fun deleteAllAgendaItems()

}