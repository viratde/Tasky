package com.tasky.agenda.network.reminder

import com.tasky.agenda.domain.model.Reminder
import com.tasky.agenda.domain.repository.remote.RemoteReminderDataSource
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
) : RemoteReminderDataSource {

    override suspend fun update(reminder: Reminder): EmptyDataResult<DataError.Network> {
        return httpClient.put(
            route = "/reminder",
            body = reminder.toReminderDto()
        )
    }

    override suspend fun create(reminder: Reminder): EmptyDataResult<DataError.Network> {
        return httpClient.post(
            route = "/reminder",
            body = reminder.toReminderDto()
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

    override suspend fun get(remainderId: String): Result<Reminder, DataError.Network> {
        return httpClient.get<ReminderDto>(
            route = "/reminder",
            queryParameters = mapOf(
                "reminderId" to remainderId
            )
        ).mapData { it.toReminder() }
    }

}