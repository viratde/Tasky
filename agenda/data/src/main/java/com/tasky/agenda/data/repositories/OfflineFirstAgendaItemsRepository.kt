package com.tasky.agenda.data.data_sources.common

import androidx.room.withTransaction
import com.tasky.agenda.data.AgendaItemsDatabase
import com.tasky.agenda.domain.repository.common.AgendaRepository
import com.tasky.agenda.domain.repository.local.LocalEventDataSource
import com.tasky.agenda.domain.repository.local.LocalReminderDataSource
import com.tasky.agenda.domain.repository.local.LocalTaskDataSource
import com.tasky.agenda.domain.repository.remote.AgendaRemoteDataSource
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import com.tasky.core.domain.util.Result
import com.tasky.core.domain.util.asEmptyDataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

class OfflineFirstAgendaItemsRepository(
    private val localEventRepository: LocalEventDataSource,
    private val localTaskRepository: LocalTaskDataSource,
    private val localReminderRepository: LocalReminderDataSource,
    private val db: AgendaItemsDatabase,
    private val agendaRemoteDataSource: AgendaRemoteDataSource,
    private val applicationScope: CoroutineScope
) : AgendaRepository {

    override suspend fun fetchAgendaItems(): EmptyDataResult<DataError> {
        return when (val result = agendaRemoteDataSource.getFullAgenda()) {
            is Result.Error -> result.asEmptyDataResult()
            is Result.Success -> {
                applicationScope.async {
                    db.withTransaction {
                        localTaskRepository.upsertAgendaItems(result.data.tasks)
                        localReminderRepository.upsertAgendaItems(result.data.reminders)
                        localEventRepository.upsertAgendaItems(result.data.events)
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
                        localTaskRepository.upsertAgendaItems(result.data.tasks)
                        localReminderRepository.upsertAgendaItems(result.data.reminders)
                        localEventRepository.upsertAgendaItems(result.data.events)
                    }
                }.await()
            }
        }
    }

}