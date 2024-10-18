package com.tasky.agenda.network.agenda

import com.tasky.agenda.domain.model.Agenda
import com.tasky.agenda.domain.model.AgendaSync
import com.tasky.agenda.domain.repository.remote.AgendaRemoteDataSource
import com.tasky.agenda.network.agenda.dtos.AgendaDto
import com.tasky.agenda.network.agenda.mappers.toAgenda
import com.tasky.agenda.network.agenda.mappers.toAgendaSyncDto
import com.tasky.core.data.networking.get
import com.tasky.core.data.networking.post
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import com.tasky.core.domain.util.Result
import com.tasky.core.domain.util.mapData
import io.ktor.client.HttpClient

class KtorAgendaRemoteDataSource(
    private val httpClient: HttpClient
) : AgendaRemoteDataSource {
    override suspend fun getAgenda(time: Long): Result<Agenda, DataError.Network> {
        return httpClient.get<AgendaDto>(
            route = "/agenda",
            queryParameters = mapOf(
                "time" to time
            )
        ).mapData { it.toAgenda() }
    }

    override suspend fun getFullAgenda(): Result<Agenda, DataError.Network> {
        return httpClient.get<AgendaDto>(
            route = "/fullAgenda"
        ).mapData { it.toAgenda() }
    }

    override suspend fun syncAgenda(agendaSync: AgendaSync): EmptyDataResult<DataError.Network> {
        return httpClient.post(
            route = "/syncAgenda",
            body = agendaSync.toAgendaSyncDto()
        )
    }
}
