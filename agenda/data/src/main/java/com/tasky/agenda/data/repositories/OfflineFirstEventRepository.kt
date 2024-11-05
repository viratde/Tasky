package com.tasky.agenda.data.repositories

import com.tasky.agenda.data.mappers.toAlarm
import com.tasky.agenda.domain.alarmScheduler.AlarmScheduler
import com.tasky.agenda.domain.model.AttendeeExistence
import com.tasky.agenda.domain.model.Event
import com.tasky.agenda.domain.repository.EventRepository
import com.tasky.agenda.domain.data_sources.local.LocalEventDataSource
import com.tasky.agenda.domain.data_sources.remote.RemoteEventDataSource
import com.tasky.agenda.domain.schedulers.EventSyncScheduler
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import com.tasky.core.domain.util.Result
import com.tasky.core.domain.util.asEmptyDataResult
import kotlinx.coroutines.flow.Flow

class OfflineFirstEventRepository(
    private val localEventDataSource: LocalEventDataSource,
    private val remoteEventDataSource: RemoteEventDataSource,
    private val eventSyncScheduler: EventSyncScheduler,
    private val alarmScheduler: AlarmScheduler
) : EventRepository {

    override suspend fun addEvent(event: Event): EmptyDataResult<DataError> {
        val localEventResult = localEventDataSource.upsertEvent(event)
        if (localEventResult !is Result.Success) {
            return localEventResult.asEmptyDataResult()
        }
        alarmScheduler.scheduleAlarm(event.toAlarm())
        return when (val remoteEventResult = remoteEventDataSource.create(event)) {
            is Result.Error -> {
                eventSyncScheduler.sync(EventSyncScheduler.SyncType.CreateEventSync(event))
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
        val localEventResult = localEventDataSource.upsertEvent(event)
        if (localEventResult !is Result.Success) {
            return localEventResult.asEmptyDataResult()
        }
        alarmScheduler.scheduleAlarm(event.toAlarm())
        return when (val remoteEventResult =
            remoteEventDataSource.update(event, deletedPhotoKeys)) {
            is Result.Error -> {
                eventSyncScheduler.sync(EventSyncScheduler.SyncType.UpdateEventSync(event))
                Result.Success(Unit)
            }

            is Result.Success -> {
                remoteEventResult.asEmptyDataResult()
            }
        }
    }

    override suspend fun getEventsByTime(time: Long): Flow<List<Event>> {
        return localEventDataSource.getEventsByTime(time)
    }

    override suspend fun deleteEventById(eventId: String) {
        localEventDataSource.deleteEvent(eventId)
        alarmScheduler.cancelAlarmById(eventId)
        val result = remoteEventDataSource.delete(eventId)
        if (result is Result.Error) {
            eventSyncScheduler.sync(EventSyncScheduler.SyncType.DeleteEventSync(eventId))
        }
    }


    override suspend fun getAttendee(email: String): Result<AttendeeExistence?, DataError.Network> {
        return remoteEventDataSource.getAttendee(email)
    }

    override suspend fun deleteLocalAttendeeFromEvent(eventId: String): EmptyDataResult<DataError.Network> {
        return remoteEventDataSource.deleteLocalAttendeeFromAnEvent(eventId)
    }

    override suspend fun getEventById(eventId: String): Event? {
        return localEventDataSource.getEventById(eventId)
    }

}