package com.tasky.agenda.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tasky.agenda.data.dao.ReminderDeleteSyncDao
import com.tasky.agenda.data.converters.EventTypeConverters
import com.tasky.agenda.data.dao.EventDao
import com.tasky.agenda.data.dao.EventDeleteSyncDao
import com.tasky.agenda.data.dao.EventSyncDao
import com.tasky.agenda.data.dao.ReminderDao
import com.tasky.agenda.data.dao.ReminderSyncDao
import com.tasky.agenda.data.dao.TaskDao
import com.tasky.agenda.data.dao.TaskDeleteSyncDao
import com.tasky.agenda.data.dao.TaskSyncDao
import com.tasky.agenda.data.model.EventDeleteSyncEntity
import com.tasky.agenda.data.model.EventEntity
import com.tasky.agenda.data.model.EventSyncEntity
import com.tasky.agenda.data.model.ReminderDeleteSyncEntity
import com.tasky.agenda.data.model.ReminderEntity
import com.tasky.agenda.data.model.ReminderSyncEntity
import com.tasky.agenda.data.model.TaskDeleteSyncEntity
import com.tasky.agenda.data.model.TaskEntity
import com.tasky.agenda.data.model.TaskSyncEntity

@Database(
    entities = [
        EventEntity::class,
        TaskEntity::class,
        ReminderEntity::class,
        EventSyncEntity::class,
        TaskSyncEntity::class,
        ReminderSyncEntity::class,
        EventDeleteSyncEntity::class,
        TaskDeleteSyncEntity::class,
        ReminderDeleteSyncEntity::class,
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(
    EventTypeConverters::class
)
abstract class AgendaItemsDatabase : RoomDatabase() {
    abstract val eventDao: EventDao
    abstract val taskDao: TaskDao
    abstract val reminderDao: ReminderDao
    abstract val eventSyncDao: EventSyncDao
    abstract val eventDeleteSyncDao: EventDeleteSyncDao
    abstract val taskSyncDao: TaskSyncDao
    abstract val taskDeleteSyncDao: TaskDeleteSyncDao
    abstract val reminderSyncDao: ReminderSyncDao
    abstract val reminderDeleteSyncDao: ReminderDeleteSyncDao
}