package com.tasky.agenda.domain.data_sources.remote

import com.tasky.agenda.domain.model.Event
import com.tasky.agenda.domain.model.AttendeeExistence
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import com.tasky.core.domain.util.Result

interface RemoteEventDataSource {

    suspend fun update(
        event: Event,
        deletedPhotoKeys: List<String>
    ): Result<Event, DataError.Network>

    suspend fun create(event: Event): Result<Event, DataError.Network>

    suspend fun delete(eventId: String): EmptyDataResult<DataError.Network>

    suspend fun get(eventId: String): Result<Event, DataError.Network>

    suspend fun getAttendee(email: String): Result<AttendeeExistence?, DataError.Network>

    suspend fun deleteLocalAttendeeFromAnEvent(eventId: String): EmptyDataResult<DataError.Network>

}