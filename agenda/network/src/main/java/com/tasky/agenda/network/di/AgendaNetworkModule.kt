package com.tasky.agenda.network.di

import com.tasky.agenda.domain.repository.remote.AgendaRemoteDataSource
import com.tasky.agenda.domain.repository.remote.RemoteEventDataSource
import com.tasky.agenda.domain.repository.remote.RemoteReminderDataSource
import com.tasky.agenda.domain.repository.remote.RemoteTaskDataSource
import com.tasky.agenda.network.agenda.KtorAgendaRemoteDataSource
import com.tasky.agenda.network.attendee.KtorAttendeeRemoteDataSource
import com.tasky.agenda.network.event.KtorRemoteEventDataSource
import com.tasky.agenda.network.reminder.KtorReminderRemoteDataSource
import com.tasky.agenda.network.task.KtorRemoteTaskDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val agendaNetworkModule = module {

    singleOf(::KtorRemoteEventDataSource).bind(RemoteEventDataSource::class)

    singleOf(::KtorRemoteTaskDataSource).bind(RemoteTaskDataSource::class)

    singleOf(::KtorReminderRemoteDataSource).bind(RemoteReminderDataSource::class)

    singleOf(::KtorAttendeeRemoteDataSource).bind(AttendeeRemoteDataSource::class)

    singleOf(::KtorAgendaRemoteDataSource).bind(AgendaRemoteDataSource::class)

}