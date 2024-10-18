package com.tasky.agenda.network.attendee

import com.tasky.agenda.domain.model.Attendee
import com.tasky.agenda.domain.repository.remote.AttendeeRemoteDataSource
import com.tasky.agenda.network.common.dtos.AttendeeDto
import com.tasky.agenda.network.common.mappers.toAttendee
import com.tasky.core.data.networking.delete
import com.tasky.core.data.networking.get
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import com.tasky.core.domain.util.Result
import com.tasky.core.domain.util.mapData
import io.ktor.client.HttpClient

class KtorAttendeeRemoteDataSource(
    private val httpClient: HttpClient
) : AttendeeRemoteDataSource {

    override suspend fun get(email: String): Result<Attendee, DataError.Network> {
        return httpClient.get<AttendeeDto>(
            route = "/attendee",
            queryParameters = mapOf(
                "email" to email
            )
        ).mapData { it.toAttendee() }
    }

    override suspend fun deleteLocalAttendeeFromAnEvent(eventId: String): EmptyDataResult<DataError.Network> {
        return httpClient.delete(
            route = "/attendee",
            queryParameters = mapOf(
                "eventId" to eventId
            )
        )
    }


}