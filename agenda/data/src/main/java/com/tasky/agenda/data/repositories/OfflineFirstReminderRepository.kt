package com.tasky.agenda.data.data_sources.common

import com.tasky.agenda.domain.model.Reminder
import com.tasky.agenda.domain.repository.common.ReminderRepository
import com.tasky.agenda.domain.repository.local.LocalReminderDataSource
import com.tasky.agenda.domain.repository.remote.RemoteReminderDataSource
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import com.tasky.core.domain.util.Result
import com.tasky.core.domain.util.asEmptyDataResult
import kotlinx.coroutines.flow.Flow

class OfflineFirstReminderRepository(
    private val localReminderDataSource: LocalReminderDataSource,
    private val remoteReminderDataSource: RemoteReminderDataSource
) : ReminderRepository {

    override suspend fun addReminder(reminder: Reminder): EmptyDataResult<DataError> {
        val localReminderResult = localReminderDataSource.upsertAgendaItem(reminder)
        if (localReminderResult !is Result.Success) {
            return localReminderResult.asEmptyDataResult()
        }
        return when (val remoteReminderResult = remoteReminderDataSource.create(reminder)) {
            is Result.Error -> {
                // @todo - i need to store that it has been yet created in remote data source
                Result.Success(Unit)
            }

            is Result.Success -> {
                remoteReminderResult.asEmptyDataResult()
            }
        }
    }

    override suspend fun updateReminder(reminder: Reminder): EmptyDataResult<DataError> {
        val localReminderResult = localReminderDataSource.upsertAgendaItem(reminder)
        if (localReminderResult !is Result.Success) {
            return localReminderResult.asEmptyDataResult()
        }
        return when (val remoteReminderResult =
            remoteReminderDataSource.update(reminder)) {
            is Result.Error -> {
                // @todo - i need to store that as it has been yet updated in remote data source
                Result.Success(Unit)
            }

            is Result.Success -> {
                remoteReminderResult.asEmptyDataResult()
            }
        }
    }

    override suspend fun getRemindersByTime(time: Long): Flow<List<Reminder>> {
        return localReminderDataSource.getAgendaItemsByTime(time)
    }

    override suspend fun deleteRemindersById(reminderId: String) {
        localReminderDataSource.deleteAgendaItem(reminderId)

        // @todo - I need to check whether it was created remotely or not
        remoteReminderDataSource.delete(reminderId)
    }

}