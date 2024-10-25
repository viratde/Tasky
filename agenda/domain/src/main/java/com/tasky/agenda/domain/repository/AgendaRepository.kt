package com.tasky.agenda.domain.repository

import com.tasky.agenda.domain.model.Agenda
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import kotlinx.coroutines.flow.Flow

interface AgendaRepository {

    suspend fun fetchAgendaItems(): EmptyDataResult<DataError>

    suspend fun fetchAgendaItemsByTime(time: Long): EmptyDataResult<DataError>

    fun getAgendaItems(): Flow<Agenda>

    fun getAgendaItemsByTime(time: Long): Flow<Agenda>
}