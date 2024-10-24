package com.tasky.agenda.domain.repository.common

import com.tasky.agenda.domain.model.Reminder
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import kotlinx.coroutines.flow.Flow

interface ReminderRepository  {

    suspend fun addReminder(reminder: Reminder): EmptyDataResult<DataError>

    suspend fun updateReminder(reminder: Reminder): EmptyDataResult<DataError>

    suspend fun getRemindersByTime(time: Long): Flow<List<Reminder>>

    suspend fun deleteRemindersById(reminderId: String)

}