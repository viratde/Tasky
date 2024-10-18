package com.tasky.agenda.network.reminder

import com.tasky.agenda.domain.model.Remainder
import com.tasky.agenda.domain.repository.remote.RemoteRemainderDataSource
import com.tasky.agenda.network.common.dtos.ReminderDto
import com.tasky.agenda.network.common.mappers.toReminder
import com.tasky.agenda.network.common.mappers.toReminderDto
import com.tasky.core.data.networking.delete
import com.tasky.core.data.networking.get
import com.tasky.core.data.networking.post
import com.tasky.core.data.networking.put
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import com.tasky.core.domain.util.Result
import com.tasky.core.domain.util.mapData
import io.ktor.client.HttpClient

class KtorReminderRemoteDataSource(
    private val httpClient: HttpClient
) : RemoteRemainderDataSource {

    override suspend fun update(remainder: Remainder): EmptyDataResult<DataError.Network> {
        return httpClient.put(
            route = "/reminder",
            body = remainder.toReminderDto()
        )
    }

    override suspend fun create(remainder: Remainder): EmptyDataResult<DataError.Network> {
        return httpClient.post(
            route = "/reminder",
            body = remainder.toReminderDto()
        )
    }

    override suspend fun delete(remainderId: String): EmptyDataResult<DataError.Network> {
        return httpClient.delete(
            route = "/reminder",
            queryParameters = mapOf(
                "reminderId" to remainderId
            )
        )
    }

    override suspend fun get(remainderId: String): Result<Remainder, DataError.Network> {
        return httpClient.get<ReminderDto>(
            route = "/reminder",
            queryParameters = mapOf(
                "reminderId" to remainderId
            )
        ).mapData { it.toReminder() }
    }

}