package com.tasky.agenda.data.di

import androidx.room.Room
import com.tasky.agenda.data.AgendaItemsDatabase
import com.tasky.agenda.data.data_sources.local.RoomEventLocalDataSource
import com.tasky.agenda.data.data_sources.local.RoomReminderLocalDataSource
import com.tasky.agenda.data.data_sources.local.RoomTaskLocalDataSource
import com.tasky.agenda.domain.model.Event
import com.tasky.agenda.domain.model.Remainder
import com.tasky.agenda.domain.model.Task
import com.tasky.agenda.domain.repository.local.LocalAgendaRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.bind
import org.koin.dsl.module

val agendaDataModule = module {

    single<AgendaItemsDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            AgendaItemsDatabase::class.java,
            "agendaItems.db"
        ).build()
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

    single<LocalAgendaRepository<Event>> {
        RoomEventLocalDataSource(get())
    }

    single<LocalAgendaRepository<Task>> {
        RoomTaskLocalDataSource(get())
    }

    single<LocalAgendaRepository<Remainder>> {
        RoomReminderLocalDataSource(get())
    }

}