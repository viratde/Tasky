package com.tasky.agenda.data.data_sources.local

import android.database.sqlite.SQLiteFullException
import com.tasky.agenda.data.dao.ReminderDao
import com.tasky.agenda.data.mappers.toReminder
import com.tasky.agenda.data.mappers.toReminderEntity
import com.tasky.agenda.domain.model.Reminder
import com.tasky.agenda.domain.data_sources.local.LocalReminderDataSource
import com.tasky.core.data.utils.getEndOfDay
import com.tasky.core.data.utils.getStartOfDay
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import com.tasky.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomReminderLocalDataSource(
    private val reminderDao: ReminderDao
) : LocalReminderDataSource {

    override suspend fun getReminderById(agendaItemId: String): Reminder? {
        return reminderDao.getReminderById(reminderId = agendaItemId)?.toReminder()
    }

    override fun getRemindersByTime(time: Long): Flow<List<Reminder>> {
        return reminderDao.getRemindersByTime(time.getStartOfDay(), time.getEndOfDay())
            .map { reminderEntities -> reminderEntities.map { it.toReminder() } }
    }

    override suspend fun upsertReminder(agendaItem: Reminder): EmptyDataResult<DataError.Local> {
        return try {
            reminderDao.upsertReminder(agendaItem.toReminderEntity())
            Result.Success(Unit)
        } catch (err: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun upsertReminders(agendaItems: List<Reminder>): EmptyDataResult<DataError.Local> {
        return try {
            reminderDao.upsertReminders(agendaItems.map { it.toReminderEntity() })
            Result.Success(Unit)
        } catch (err: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteReminder(agendaItemId: String) {
        reminderDao.deleteReminder(agendaItemId)
    }

    override fun getReminders(): Flow<List<Reminder>> {
        return reminderDao.getReminders()
            .map { reminderEntities -> reminderEntities.map { it.toReminder() } }
    }

    override suspend fun deleteAllReminders() {
        return reminderDao.deleteAllReminders()
    }
}