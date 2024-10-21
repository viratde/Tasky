package com.tasky.agenda.domain.repository.common

import com.tasky.agenda.domain.model.Event
import com.tasky.agenda.domain.model.TemporaryNetworkAttendee
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import com.tasky.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface EventRepository {

    suspend fun addEvent(event: Event): EmptyDataResult<DataError>

    suspend fun updateEvent(
        event: Event,
        deletedPhotoKeys: List<String>
    ): EmptyDataResult<DataError>

    suspend fun getEventsByTime(time: Long): Flow<List<Event>>

    suspend fun deleteEventById(eventId: String)

    suspend fun getAttendee(email: String): Result<TemporaryNetworkAttendee?, DataError.Network>

    suspend fun deleteLocalAttendeeFromEvent(eventId: String): EmptyDataResult<DataError.Network>

}