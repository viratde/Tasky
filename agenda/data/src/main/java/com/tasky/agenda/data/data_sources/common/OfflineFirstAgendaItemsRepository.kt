package com.tasky.agenda.data.data_sources.common

import com.tasky.agenda.domain.model.Event
import com.tasky.agenda.domain.model.Remainder
import com.tasky.agenda.domain.model.Task
import com.tasky.agenda.domain.model.TemporaryNetworkAttendee
import com.tasky.agenda.domain.repository.common.AgendaItemRepository
import com.tasky.agenda.domain.repository.local.LocalAgendaRepository
import com.tasky.agenda.domain.repository.remote.AgendaRemoteDataSource
import com.tasky.agenda.domain.repository.remote.AttendeeRemoteDataSource
import com.tasky.agenda.domain.repository.remote.RemoteEventDataSource
import com.tasky.agenda.domain.repository.remote.RemoteRemainderDataSource
import com.tasky.agenda.domain.repository.remote.RemoteTaskDataSource
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import com.tasky.core.domain.util.Result
import com.tasky.core.domain.util.asEmptyDataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

class OfflineFirstAgendaItemsRepository(
    private val localEventRepository: LocalAgendaRepository<Event>,
    private val localTaskRepository: LocalAgendaRepository<Task>,
    private val localReminderRepository: LocalAgendaRepository<Remainder>,
    private val remoteEventDataSource: RemoteEventDataSource,
    private val remoteTaskDataSource: RemoteTaskDataSource,
    private val remoteRemainderDataSource: RemoteRemainderDataSource,
    private val agendaRemoteDataSource: AgendaRemoteDataSource,
    private val attendeeRemoteDataSource: AttendeeRemoteDataSource,
    private val applicationScope: CoroutineScope
) : AgendaItemRepository {

    override suspend fun fetchAgendaItems(): EmptyDataResult<DataError> {
        return when (val result = agendaRemoteDataSource.getFullAgenda()) {
            is Result.Error -> result.asEmptyDataResult()
            is Result.Success -> {
                applicationScope.async {
                    localTaskRepository.upsertAgendaItems(result.data.tasks)
                    localReminderRepository.upsertAgendaItems(result.data.reminders)
                    localEventRepository.upsertAgendaItems(result.data.events)
                }.await()
            }
        }
    }

    override suspend fun fetchAgendaItemsByTime(time: Long): EmptyDataResult<DataError> {
        return when (val result = agendaRemoteDataSource.getAgenda(time)) {
            is Result.Error -> result.asEmptyDataResult()
            is Result.Success -> {
                applicationScope.async {
                    localTaskRepository.upsertAgendaItems(result.data.tasks)
                    localReminderRepository.upsertAgendaItems(result.data.reminders)
                    localEventRepository.upsertAgendaItems(result.data.events)
                }.await()
            }
        }
    }

    override suspend fun addEvent(event: Event): EmptyDataResult<DataError> {
        val localEventResult = localEventRepository.upsertAgendaItem(event)
        if (localEventResult !is Result.Success) {
            return localEventResult.asEmptyDataResult()
        }
        return when (val remoteEventResult = remoteEventDataSource.create(event)) {
            is Result.Error -> {
                // @todo - i need to store that it has been yet created in remote data source
                Result.Success(Unit)
            }

            is Result.Success -> {
                remoteEventResult.asEmptyDataResult()
            }
        }
    }

    override suspend fun updateEvent(
        event: Event,
        deletedPhotoKeys: List<String>
    ): EmptyDataResult<DataError> {
        val localEventResult = localEventRepository.upsertAgendaItem(event)
        if (localEventResult !is Result.Success) {
            return localEventResult.asEmptyDataResult()
        }
        return when (val remoteEventResult =
            remoteEventDataSource.update(event, deletedPhotoKeys)) {
            is Result.Error -> {
                // @todo - i need to store that it has been yet updated in remote data source
                Result.Success(Unit)
            }

            is Result.Success -> {
                remoteEventResult.asEmptyDataResult()
            }
        }
    }

    override suspend fun getEventsByTime(time: Long): Flow<List<Event>> {
        return localEventRepository.getAgendaItemsByTime(time)
    }

    override suspend fun deleteEventById(eventId: String) {
        localEventRepository.deleteAgendaItem(eventId)

        // @todo - I need to check whether it was created remotely or not
        remoteEventDataSource.delete(eventId)
    }

    override suspend fun addTask(task: Task): EmptyDataResult<DataError> {
        val localTaskResult = localTaskRepository.upsertAgendaItem(task)
        if (localTaskResult !is Result.Success) {
            return localTaskResult.asEmptyDataResult()
        }
        return when (val remoteTaskResult = remoteTaskDataSource.create(task)) {
            is Result.Error -> {
                // @todo - i need to store that it has been yet created in remote data source
                Result.Success(Unit)
            }

            is Result.Success -> {
                remoteTaskResult.asEmptyDataResult()
            }
        }
    }

    override suspend fun updateTask(task: Task): EmptyDataResult<DataError> {
        val localTaskResult = localTaskRepository.upsertAgendaItem(task)
        if (localTaskResult !is Result.Success) {
            return localTaskResult.asEmptyDataResult()
        }
        return when (val remoteTaskResult =
            remoteTaskDataSource.update(task)) {
            is Result.Error -> {
                // @todo - i need to store that as it has been yet updated in remote data source
                Result.Success(Unit)
            }

            is Result.Success -> {
                remoteTaskResult.asEmptyDataResult()
            }
        }
    }

    override suspend fun getTasksByTime(time: Long): Flow<List<Task>> {
        return localTaskRepository.getAgendaItemsByTime(time)
    }

    override suspend fun deleteTaskById(taskId: String) {
        localTaskRepository.deleteAgendaItem(taskId)

        // @todo - I need to check whether it was created remotely or not
        remoteTaskDataSource.delete(taskId)
    }

    override suspend fun addReminder(remainder: Remainder): EmptyDataResult<DataError> {
        val localReminderResult = localReminderRepository.upsertAgendaItem(remainder)
        if (localReminderResult !is Result.Success) {
            return localReminderResult.asEmptyDataResult()
        }
        return when (val remoteReminderResult = remoteRemainderDataSource.create(remainder)) {
            is Result.Error -> {
                // @todo - i need to store that it has been yet created in remote data source
                Result.Success(Unit)
            }

            is Result.Success -> {
                remoteReminderResult.asEmptyDataResult()
            }
        }
    }

    override suspend fun updateReminder(remainder: Remainder): EmptyDataResult<DataError> {
        val localReminderResult = localReminderRepository.upsertAgendaItem(remainder)
        if (localReminderResult !is Result.Success) {
            return localReminderResult.asEmptyDataResult()
        }
        return when (val remoteReminderResult =
            remoteRemainderDataSource.update(remainder)) {
            is Result.Error -> {
                // @todo - i need to store that as it has been yet updated in remote data source
                Result.Success(Unit)
            }

            is Result.Success -> {
                remoteReminderResult.asEmptyDataResult()
            }
        }
    }

    override suspend fun getRemindersByTime(time: Long): Flow<List<Remainder>> {
        return localReminderRepository.getAgendaItemsByTime(time)
    }

    override suspend fun deleteRemindersById(reminderId: String) {
        localReminderRepository.deleteAgendaItem(reminderId)

        // @todo - I need to check whether it was created remotely or not
        remoteRemainderDataSource.delete(reminderId)
    }

    override suspend fun getAttendee(email: String): Result<TemporaryNetworkAttendee?, DataError.Network> {
        return attendeeRemoteDataSource.get(email)
    }

    override suspend fun deleteLocalAttendeeFromEvent(eventId: String): EmptyDataResult<DataError.Network> {
        return attendeeRemoteDataSource.deleteLocalAttendeeFromAnEvent(eventId)
    }
}