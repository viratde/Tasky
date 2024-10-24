package com.tasky.agenda.domain.data_sources.remote

import com.tasky.agenda.domain.model.Agenda
import com.tasky.agenda.domain.model.AgendaSync
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import com.tasky.core.domain.util.Result

interface AgendaRemoteDataSource {

    suspend fun getAgenda(time: Long): Result<Agenda, DataError.Network>

    suspend fun getFullAgenda(): Result<Agenda, DataError.Network>

    suspend fun syncAgenda(agendaSync: AgendaSync): EmptyDataResult<DataError.Network>

}