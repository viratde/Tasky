package com.tasky.agenda.domain.repository.common

import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult

interface AgendaRepository {

    suspend fun fetchAgendaItems(): EmptyDataResult<DataError>

    suspend fun fetchAgendaItemsByTime(time: Long): EmptyDataResult<DataError>

}