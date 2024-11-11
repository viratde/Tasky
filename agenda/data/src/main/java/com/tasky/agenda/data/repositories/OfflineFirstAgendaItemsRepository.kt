package com.tasky.agenda.data.repositories

import androidx.room.withTransaction
import com.tasky.agenda.data.AgendaItemsDatabase
import com.tasky.agenda.domain.repository.AgendaRepository
import com.tasky.agenda.domain.data_sources.local.LocalEventDataSource
import com.tasky.agenda.domain.data_sources.local.LocalReminderDataSource
import com.tasky.agenda.domain.data_sources.local.LocalTaskDataSource
import com.tasky.agenda.domain.data_sources.remote.AgendaRemoteDataSource
import com.tasky.agenda.domain.model.Agenda
import com.tasky.core.data.networking.get
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import com.tasky.core.domain.util.Result
import com.tasky.core.domain.util.asEmptyDataResult
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import io.ktor.client.plugins.plugin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class OfflineFirstAgendaItemsRepository(
    private val localEventDataSource: LocalEventDataSource,
    private val localTaskDataSource: LocalTaskDataSource,
    private val localReminderDataSource: LocalReminderDataSource,
    private val db: AgendaItemsDatabase,
    private val agendaRemoteDataSource: AgendaRemoteDataSource,
    private val applicationScope: CoroutineScope,
    private val client: HttpClient
) : AgendaRepository {

    override suspend fun fetchAgendaItems(): EmptyDataResult<DataError> {
        return when (val result = agendaRemoteDataSource.getFullAgenda()) {
            is Result.Error -> result.asEmptyDataResult()
            is Result.Success -> {
                applicationScope.async {
                    db.withTransaction {
                        localTaskDataSource.upsertTasks(result.data.tasks)
                        localReminderDataSource.upsertReminders(result.data.reminders)
                        localEventDataSource.upsertEvents(result.data.events)
                    }
                }.await()
            }
        }
    }

    override suspend fun fetchAgendaItemsByTime(time: Long): EmptyDataResult<DataError> {
        return when (val result = agendaRemoteDataSource.getAgenda(time)) {
            is Result.Error -> result.asEmptyDataResult()
            is Result.Success -> {
                applicationScope.async {
                    db.withTransaction {
                        localTaskDataSource.upsertTasks(result.data.tasks)
                        localReminderDataSource.upsertReminders(result.data.reminders)
                        localEventDataSource.upsertEvents(result.data.events)
                    }
                }.await()
            }
        }
    }

    override fun getAgendaItems(): Flow<Agenda> {
        return combine(
            localEventDataSource.getEvents(),
            localTaskDataSource.getTasks(),
            localReminderDataSource.getReminders()
        ) { events, tasks, reminders ->
            Agenda(
                events = events,
                tasks = tasks,
                reminders = reminders
            )
        }
    }

    override fun getAgendaItemsByTime(time: Long): Flow<Agenda> {
        return combine(
            localEventDataSource.getEventsByTime(time),
            localTaskDataSource.getTasksByTime(time),
            localReminderDataSource.getRemindersByTime(time)
        ) { events, tasks, reminders ->
            Agenda(
                events = events,
                tasks = tasks,
                reminders = reminders
            )
        }
    }

    override suspend fun getAllAgendaItemsGraterThanTime(time: Long): Agenda {
        return Agenda(
            events = localEventDataSource.getAllEventsGreaterThanTime(time),
            tasks = localTaskDataSource.getAllTasksGreaterThanTime(time),
            reminders = localReminderDataSource.getAllRemindersGreaterThanTime(time)
        )
    }

    override suspend fun deleteAllAgendaItems() {
        localTaskDataSource.deleteAllTasks()
        localEventDataSource.deleteAllEvents()
        localTaskDataSource.deleteAllTasks()
    }

    override suspend fun logout(): EmptyDataResult<DataError.Network> {
        val result = client.get<Unit>(
            route = "/logout",
        ).asEmptyDataResult()

        client.plugin(Auth).providers.filterIsInstance<BearerAuthProvider>()
            .firstOrNull()
            ?.clearToken()

        return result
    }

}