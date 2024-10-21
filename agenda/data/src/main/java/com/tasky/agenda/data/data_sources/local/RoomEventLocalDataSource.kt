package com.tasky.agenda.data.data_sources.local

import android.database.sqlite.SQLiteFullException
import com.tasky.agenda.data.dao.EventDao
import com.tasky.agenda.data.mappers.toEvent
import com.tasky.agenda.data.mappers.toEventEntity
import com.tasky.agenda.domain.model.Event
import com.tasky.agenda.domain.repository.local.LocalAgendaDataSource
import com.tasky.core.data.utils.getEndOfDay
import com.tasky.core.data.utils.getStartOfDay
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import com.tasky.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomEventLocalDataSource(
    private val eventDao: EventDao
) : LocalAgendaDataSource<Event> {

    override suspend fun getAgendaItemsById(agendaItemId: String): Event? {
        return eventDao.getEventById(eventId = agendaItemId)?.toEvent()
    }

    override suspend fun getAgendaItemsByTime(time: Long): List<Event> {
        return eventDao.getEventsByTime(time.getStartOfDay(), time.getEndOfDay())
            .map { it.toEvent() }
    }

    override suspend fun upsertAgendaItem(agendaItem: Event): EmptyDataResult<DataError.Local> {
        return try {
            eventDao.upsertEvent(agendaItem.toEventEntity())
            Result.Success(Unit)
        } catch (err: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun upsertAgendaItems(agendaItems: List<Event>): EmptyDataResult<DataError.Local> {
        return try {
            eventDao.upsertEvents(agendaItems.map { it.toEventEntity() })
            Result.Success(Unit)
        } catch (err: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteAgendaItem(agendaItemId: String) {
        eventDao.deleteEvent(agendaItemId)
    }

    override suspend fun getAgendaItems(): Flow<List<Event>> {
        return eventDao.getEvents().map { eventEntities -> eventEntities.map { it.toEvent() } }
    }

    override suspend fun deleteAllAgendaItems() {
        return eventDao.deleteEvents()
    }
}