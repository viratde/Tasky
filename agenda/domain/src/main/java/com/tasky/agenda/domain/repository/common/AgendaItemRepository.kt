package com.tasky.agenda.domain.repository.common

import com.tasky.agenda.domain.model.Event
import com.tasky.agenda.domain.model.Remainder
import com.tasky.agenda.domain.model.Task
import com.tasky.agenda.domain.model.TemporaryNetworkAttendee
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import com.tasky.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface AgendaItemRepository {

    suspend fun fetchAgendaItems(): EmptyDataResult<DataError>

    suspend fun fetchAgendaItemsByTime(time: Long): EmptyDataResult<DataError>

    suspend fun addEvent(event: Event): EmptyDataResult<DataError>

    suspend fun updateEvent(
        event: Event,
        deletedPhotoKeys: List<String>
    ): EmptyDataResult<DataError>

    suspend fun getEventsByTime(time: Long): Flow<List<Event>>

    suspend fun deleteEventById(eventId: String)

    suspend fun addTask(task: Task): EmptyDataResult<DataError>

    suspend fun updateTask(task: Task): EmptyDataResult<DataError>

    suspend fun getTasksByTime(time: Long): Flow<List<Task>>

    suspend fun deleteTaskById(taskId: String)

    suspend fun addReminder(remainder: Remainder): EmptyDataResult<DataError>

    suspend fun updateReminder(remainder: Remainder): EmptyDataResult<DataError>

    suspend fun getRemindersByTime(time: Long): Flow<List<Remainder>>

    suspend fun deleteRemindersById(reminderId: String)

    suspend fun getAttendee(email: String): Result<TemporaryNetworkAttendee?, DataError.Network>

    suspend fun deleteLocalAttendeeFromEvent(eventId: String): EmptyDataResult<DataError.Network>
}