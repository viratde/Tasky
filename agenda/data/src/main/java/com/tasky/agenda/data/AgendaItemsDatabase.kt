package com.tasky.agenda.data

import android.provider.CalendarContract.EventsEntity
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tasky.agenda.data.converters.EventTypeConverters
import com.tasky.agenda.data.dao.EventDao
import com.tasky.agenda.data.dao.ReminderDao
import com.tasky.agenda.data.dao.TaskDao
import com.tasky.agenda.data.model.ReminderEntity
import com.tasky.agenda.data.model.TaskEntity

@Database(
    entities = [EventsEntity::class, TaskEntity::class, ReminderEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    EventTypeConverters::class
)
abstract class AgendaItemsDatabase : RoomDatabase() {
    abstract val eventDao: EventDao
    abstract val taskDao: TaskDao
    abstract val reminderDao: ReminderDao
}