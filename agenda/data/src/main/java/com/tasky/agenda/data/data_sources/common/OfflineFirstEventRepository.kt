package com.tasky.agenda.data.data_sources.common

import com.tasky.agenda.domain.model.AttendeeExistence
import com.tasky.agenda.domain.model.Event
import com.tasky.agenda.domain.repository.common.EventRepository
import com.tasky.agenda.domain.repository.local.LocalEventDataSource
import com.tasky.agenda.domain.repository.remote.RemoteEventDataSource
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import com.tasky.core.domain.util.Result
import com.tasky.core.domain.util.asEmptyDataResult
import kotlinx.coroutines.flow.Flow

class OfflineFirstEventRepository(
    private val localEventDataSource: LocalEventDataSource,
    private val remoteEventDataSource: RemoteEventDataSource
) : EventRepository {

    override suspend fun addEvent(event: Event): EmptyDataResult<DataError> {
        val localEventResult = localEventDataSource.upsertAgendaItem(event)
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
        val localEventResult = localEventDataSource.upsertAgendaItem(event)
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
        return localEventDataSource.getAgendaItemsByTime(time)
    }

    override suspend fun deleteEventById(eventId: String) {
        localEventDataSource.deleteAgendaItem(eventId)

        // @todo - I need to check whether it was created remotely or not
        remoteEventDataSource.delete(eventId)
    }


    override suspend fun getAttendee(email: String): Result<AttendeeExistence?, DataError.Network> {
        return remoteEventDataSource.getAttendee(email)
    }

    override suspend fun deleteLocalAttendeeFromEvent(eventId: String): EmptyDataResult<DataError.Network> {
        return remoteEventDataSource.deleteLocalAttendeeFromAnEvent(eventId)
    }

}