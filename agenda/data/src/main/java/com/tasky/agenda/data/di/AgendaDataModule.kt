package com.tasky.agenda.data.di

import androidx.room.Room
import com.tasky.agenda.data.AgendaItemsDatabase
import com.tasky.agenda.data.data_sources.common.OfflineFirstAgendaItemsRepository
import com.tasky.agenda.data.data_sources.common.OfflineFirstEventRepository
import com.tasky.agenda.data.data_sources.common.OfflineFirstReminderRepository
import com.tasky.agenda.data.data_sources.common.OfflineFirstTaskRepository
import com.tasky.agenda.data.data_sources.local.RoomEventLocalDataSource
import com.tasky.agenda.data.data_sources.local.RoomReminderLocalDataSource
import com.tasky.agenda.data.data_sources.local.RoomTaskLocalDataSource
import com.tasky.agenda.domain.model.Event
import com.tasky.agenda.domain.model.Reminder
import com.tasky.agenda.domain.model.Task
import com.tasky.agenda.domain.repository.common.AgendaRepository
import com.tasky.agenda.domain.repository.common.EventRepository
import com.tasky.agenda.domain.repository.common.ReminderRepository
import com.tasky.agenda.domain.repository.common.TaskRepository
import com.tasky.agenda.domain.repository.local.LocalAgendaDataSource
import org.koin.android.ext.koin.androidApplication
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

    singleOf(::RoomEventLocalDataSource).bind<LocalAgendaDataSource<Event>>()

    singleOf(::RoomTaskLocalDataSource).bind<LocalAgendaDataSource<Task>>()

    singleOf(::RoomReminderLocalDataSource).bind<LocalAgendaDataSource<Reminder>>()

    singleOf(::OfflineFirstAgendaItemsRepository).bind<AgendaRepository>()

    singleOf(::OfflineFirstEventRepository).bind<EventRepository>()

    singleOf(::OfflineFirstTaskRepository).bind<TaskRepository>()

    singleOf(::OfflineFirstReminderRepository).bind<ReminderRepository>()

}