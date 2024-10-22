package com.tasky.agenda.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.tasky.agenda.data.model.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    @Query("SELECT * FROM reminderEntity WHERE id=:reminderId")
    suspend fun getReminderById(reminderId: String): ReminderEntity?

    @Query("SELECT * FROM reminderEntity")
    fun getReminders(): Flow<List<ReminderEntity>>

    @Query("SELECT * FROM reminderEntity WHERE time BETWEEN :startTime and :endTime")
    fun getRemindersByTime(startTime: Long, endTime: Long): Flow<List<ReminderEntity>>

    @Upsert
    suspend fun upsertReminder(reminderEntity: ReminderEntity)

    @Upsert
    suspend fun upsertReminders(reminderEntities: List<ReminderEntity>)

    @Query("DELETE FROM reminderEntity WHERE id=:reminderId")
    suspend fun deleteReminder(reminderId: String)

    @Query("DELETE FROM reminderEntity")
    suspend fun deleteAllReminders()

}