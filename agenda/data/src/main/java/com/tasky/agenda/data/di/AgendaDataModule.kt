package com.tasky.agenda.data.di

import androidx.room.Room
import com.tasky.agenda.data.AgendaItemsDatabase
import com.tasky.agenda.data.alarmScheduler.AlarmSchedulerImpl
import com.tasky.agenda.data.repositories.OfflineFirstAgendaItemsRepository
import com.tasky.agenda.data.repositories.OfflineFirstEventRepository
import com.tasky.agenda.data.repositories.OfflineFirstReminderRepository
import com.tasky.agenda.data.repositories.OfflineFirstTaskRepository
import com.tasky.agenda.data.data_sources.local.RoomEventLocalDataSource
import com.tasky.agenda.data.data_sources.local.RoomReminderLocalDataSource
import com.tasky.agenda.data.data_sources.local.RoomTaskLocalDataSource
import com.tasky.agenda.data.schedulers.EventWorkSyncScheduler
import com.tasky.agenda.data.schedulers.ReminderWorkSyncScheduler
import com.tasky.agenda.data.schedulers.TaskWorkSyncScheduler
import com.tasky.agenda.data.utils.ImageCompressorImpl
import com.tasky.agenda.data.workers.event.CreateEventWorker
import com.tasky.agenda.data.workers.event.DeleteEventWorker
import com.tasky.agenda.data.workers.event.UpdateEventWorker
import com.tasky.agenda.data.workers.task.CreateTaskWorker
import com.tasky.agenda.data.workers.task.DeleteTaskWorker
import com.tasky.agenda.data.workers.task.UpdateTaskWorker
import com.tasky.agenda.data.workers.reminder.CreateReminderWorker
import com.tasky.agenda.data.workers.reminder.DeleteReminderWorker
import com.tasky.agenda.data.workers.reminder.UpdateReminderWorker
import com.tasky.agenda.domain.alarmScheduler.AlarmScheduler
import com.tasky.agenda.domain.repository.AgendaRepository
import com.tasky.agenda.domain.repository.EventRepository
import com.tasky.agenda.domain.repository.ReminderRepository
import com.tasky.agenda.domain.repository.TaskRepository
import com.tasky.agenda.domain.data_sources.local.LocalEventDataSource
import com.tasky.agenda.domain.data_sources.local.LocalReminderDataSource
import com.tasky.agenda.domain.data_sources.local.LocalTaskDataSource
import com.tasky.agenda.domain.schedulers.EventSyncScheduler
import com.tasky.agenda.domain.schedulers.ReminderSyncScheduler
import com.tasky.agenda.domain.schedulers.TaskSyncScheduler
import com.tasky.agenda.domain.utils.ImageCompressor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val agendaDataModule = module {

    single<AgendaItemsDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            AgendaItemsDatabase::class.java,
            "agendaItems.db"
        ).fallbackToDestructiveMigration().build()
    }

    single {
        get<AgendaItemsDatabase>().eventDao
    }

    single {
        get<AgendaItemsDatabase>().taskDao
    }

    single {
        get<AgendaItemsDatabase>().reminderDao
    }

    single {
        get<AgendaItemsDatabase>().eventSyncDao
    }

    single {
        get<AgendaItemsDatabase>().eventDeleteSyncDao
    }

    single {
        get<AgendaItemsDatabase>().taskSyncDao
    }

    single {
        get<AgendaItemsDatabase>().taskDeleteSyncDao
    }

    single {
        get<AgendaItemsDatabase>().reminderSyncDao
    }

    single {
        get<AgendaItemsDatabase>().reminderDeleteSyncDao
    }

    singleOf(::RoomEventLocalDataSource).bind<LocalEventDataSource>()

    singleOf(::RoomTaskLocalDataSource).bind<LocalTaskDataSource>()

    singleOf(::RoomReminderLocalDataSource).bind<LocalReminderDataSource>()

    singleOf(::OfflineFirstAgendaItemsRepository).bind<AgendaRepository>()

    singleOf(::OfflineFirstEventRepository).bind<EventRepository>()

    singleOf(::OfflineFirstTaskRepository).bind<TaskRepository>()

    singleOf(::OfflineFirstReminderRepository).bind<ReminderRepository>()

    singleOf(::ImageCompressorImpl).bind<ImageCompressor>()

    singleOf(::EventWorkSyncScheduler).bind<EventSyncScheduler>()

    singleOf(::TaskWorkSyncScheduler).bind<TaskSyncScheduler>()

    singleOf(::ReminderWorkSyncScheduler).bind<ReminderSyncScheduler>()

    workerOf(::CreateEventWorker)

    workerOf(::UpdateEventWorker)

    workerOf(::DeleteEventWorker)

    workerOf(::CreateTaskWorker)

    workerOf(::UpdateTaskWorker)

    workerOf(::DeleteTaskWorker)

    workerOf(::CreateReminderWorker)

    workerOf(::UpdateReminderWorker)

    workerOf(::DeleteReminderWorker)

    singleOf(::AlarmSchedulerImpl).bind<AlarmScheduler>()

}