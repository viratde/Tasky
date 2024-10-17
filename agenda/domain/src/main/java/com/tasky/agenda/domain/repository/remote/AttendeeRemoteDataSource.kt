package com.tasky.agenda.domain.repository.remote

import com.tasky.agenda.domain.model.Attendee
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import com.tasky.core.domain.util.Result

interface AttendeeRemoteDataSource {

    suspend fun get(attendeeId: String): Result<Attendee, DataError.Network>

    suspend fun deleteAttendeeFromAnEvent(eventId: String): EmptyDataResult<DataError.Network>

}