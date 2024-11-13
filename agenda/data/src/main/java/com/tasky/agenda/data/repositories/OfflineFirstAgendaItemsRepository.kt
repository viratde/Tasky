package com.tasky.agenda.data.repositories

import androidx.room.withTransaction
import com.tasky.agenda.data.AgendaItemsDatabase
import com.tasky.agenda.data.dao.EventDeleteSyncDao
import com.tasky.agenda.data.dao.EventSyncDao
import com.tasky.agenda.data.dao.ReminderDeleteSyncDao
import com.tasky.agenda.data.dao.ReminderSyncDao
import com.tasky.agenda.data.dao.TaskDeleteSyncDao
import com.tasky.agenda.data.dao.TaskSyncDao
import com.tasky.agenda.data.mappers.toEvent
import com.tasky.agenda.data.mappers.toReminder
import com.tasky.agenda.data.mappers.toTask
import com.tasky.agenda.data.model.SyncType
import com.tasky.agenda.domain.repository.AgendaRepository
import com.tasky.agenda.domain.data_sources.local.LocalEventDataSource
import com.tasky.agenda.domain.data_sources.local.LocalReminderDataSource
import com.tasky.agenda.domain.data_sources.local.LocalTaskDataSource
import com.tasky.agenda.domain.data_sources.remote.AgendaRemoteDataSource
import com.tasky.agenda.domain.model.Agenda
import com.tasky.agenda.domain.schedulers.EventSyncScheduler
import com.tasky.agenda.domain.schedulers.ReminderSyncScheduler
import com.tasky.agenda.domain.schedulers.TaskSyncScheduler
import com.tasky.core.data.networking.get
import com.tasky.core.domain.AuthInfoStorage
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import com.tasky.core.domain.util.Result
import com.tasky.core.domain.util.asEmptyDataResult
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import io.ktor.client.plugins.plugin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext

class OfflineFirstAgendaItemsRepository(
    private val localEventDataSource: LocalEventDataSource,
    private val localTaskDataSource: LocalTaskDataSource,
    private val localReminderDataSource: LocalReminderDataSource,
    private val db: AgendaItemsDatabase,
    private val agendaRemoteDataSource: AgendaRemoteDataSource,
    private val applicationScope: CoroutineScope,
    private val client: HttpClient,
    private val authInfoStorage: AuthInfoStorage,
    private val eventSyncDao: EventSyncDao,
    private val taskSyncDao: TaskSyncDao,
    private val reminderSyncDao: ReminderSyncDao,
    private val eventDeleteSyncDao: EventDeleteSyncDao,
    private val taskDeleteSyncDao: TaskDeleteSyncDao,
    private val reminderDeleteSyncDao: ReminderDeleteSyncDao,
    private val eventSyncScheduler: EventSyncScheduler,
    private val taskSyncScheduler: TaskSyncScheduler,
    private val reminderSyncScheduler: ReminderSyncScheduler
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

    override suspend fun syncPendingAgendaItems() {
        withContext(Dispatchers.IO) {
            val userId = authInfoStorage.get()?.userId ?: return@withContext

            eventSyncDao.getAllEventPendingSyncs(
                syncType = SyncType.CREATE,
                userId = userId
            ).forEach {
                eventSyncScheduler.sync(EventSyncScheduler.SyncType.CreateEventSync(it.event.toEvent()))
            }
            eventSyncDao.getAllEventPendingSyncs(
                syncType = SyncType.UPDATE,
                userId = userId
            ).forEach {
                eventSyncScheduler.sync(EventSyncScheduler.SyncType.UpdateEventSync(it.event.toEvent()))
            }
            eventDeleteSyncDao.getAllEventDeletePendingSyncs(
                userId = userId
            ).forEach {
                eventSyncScheduler.sync(EventSyncScheduler.SyncType.DeleteEventSync(it.eventId))
            }

            taskSyncDao.getAllTaskPendingSyncs(
                syncType = SyncType.CREATE,
                userId = userId
            ).forEach {
                taskSyncScheduler.sync(TaskSyncScheduler.SyncType.CreateTaskSync(it.task.toTask()))
            }
            taskSyncDao.getAllTaskPendingSyncs(
                syncType = SyncType.UPDATE,
                userId = userId
            ).forEach {
                taskSyncScheduler.sync(TaskSyncScheduler.SyncType.UpdateTaskSync(it.task.toTask()))
            }
            taskDeleteSyncDao.getAllTaskDeletePendingSyncs(
                userId = userId
            ).forEach {
                taskSyncScheduler.sync(TaskSyncScheduler.SyncType.DeleteTaskSync(it.taskId))
            }

            reminderSyncDao.getAllReminderPendingSyncs(
                syncType = SyncType.CREATE,
                userId = userId
            ).forEach {
                reminderSyncScheduler.sync(ReminderSyncScheduler.SyncType.CreateReminderSync(it.reminder.toReminder()))
            }
            reminderSyncDao.getAllReminderPendingSyncs(
                syncType = SyncType.UPDATE,
                userId = userId
            ).forEach {
                reminderSyncScheduler.sync(ReminderSyncScheduler.SyncType.UpdateReminderSync(it.reminder.toReminder()))
            }
            reminderDeleteSyncDao.getAllReminderDeletePendingSyncs(
                userId = userId
            ).forEach {
                reminderSyncScheduler.sync(ReminderSyncScheduler.SyncType.DeleteReminderSync(it.reminderId))
            }

        }
    }

}