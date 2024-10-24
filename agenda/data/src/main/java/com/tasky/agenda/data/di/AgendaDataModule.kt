package com.tasky.agenda.data.di

import androidx.room.Room
import com.tasky.agenda.data.AgendaItemsDatabase
import com.tasky.agenda.data.repositories.OfflineFirstAgendaItemsRepository
import com.tasky.agenda.data.repositories.OfflineFirstEventRepository
import com.tasky.agenda.data.repositories.OfflineFirstReminderRepository
import com.tasky.agenda.data.repositories.OfflineFirstTaskRepository
import com.tasky.agenda.data.data_sources.local.RoomEventLocalDataSource
import com.tasky.agenda.data.data_sources.local.RoomReminderLocalDataSource
import com.tasky.agenda.data.data_sources.local.RoomTaskLocalDataSource
import com.tasky.agenda.domain.repository.AgendaRepository
import com.tasky.agenda.domain.repository.EventRepository
import com.tasky.agenda.domain.repository.ReminderRepository
import com.tasky.agenda.domain.repository.TaskRepository
import com.tasky.agenda.domain.data_sources.local.LocalEventDataSource
import com.tasky.agenda.domain.data_sources.local.LocalReminderDataSource
import com.tasky.agenda.domain.data_sources.local.LocalTaskDataSource
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

    singleOf(::RoomEventLocalDataSource).bind<LocalEventDataSource>()

    singleOf(::RoomTaskLocalDataSource).bind<LocalTaskDataSource>()

    singleOf(::RoomReminderLocalDataSource).bind<LocalReminderDataSource>()

    singleOf(::OfflineFirstAgendaItemsRepository).bind<AgendaRepository>()

    singleOf(::OfflineFirstEventRepository).bind<EventRepository>()

    singleOf(::OfflineFirstTaskRepository).bind<TaskRepository>()

    singleOf(::OfflineFirstReminderRepository).bind<ReminderRepository>()

}