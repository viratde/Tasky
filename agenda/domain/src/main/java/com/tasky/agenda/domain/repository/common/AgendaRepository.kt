package com.tasky.agenda.domain.repository.common

import com.tasky.agenda.domain.model.Event
import com.tasky.agenda.domain.model.Remainder
import com.tasky.agenda.domain.model.Task
import com.tasky.agenda.domain.model.TemporaryNetworkAttendee
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import com.tasky.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface AgendaItemRepository {

    suspend fun fetchAgendaItems(): EmptyDataResult<DataError>

    suspend fun fetchAgendaItemsByTime(time: Long): EmptyDataResult<DataError>


    suspend fun getAttendee(email: String): Result<TemporaryNetworkAttendee?, DataError.Network>

    suspend fun deleteLocalAttendeeFromEvent(eventId: String): EmptyDataResult<DataError.Network>
}