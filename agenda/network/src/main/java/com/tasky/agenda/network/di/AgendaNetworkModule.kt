package com.tasky.agenda.network.di

import com.tasky.agenda.domain.data_sources.remote.AgendaRemoteDataSource
import com.tasky.agenda.domain.data_sources.remote.RemoteEventDataSource
import com.tasky.agenda.domain.data_sources.remote.RemoteReminderDataSource
import com.tasky.agenda.domain.data_sources.remote.RemoteTaskDataSource
import com.tasky.agenda.network.agenda.KtorAgendaRemoteDataSource
import com.tasky.agenda.network.event.KtorRemoteEventDataSource
import com.tasky.agenda.network.reminder.KtorReminderRemoteDataSource
import com.tasky.agenda.network.task.KtorRemoteTaskDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val agendaNetworkModule = module {

    singleOf(::KtorRemoteEventDataSource).bind<RemoteEventDataSource>()

    singleOf(::KtorRemoteTaskDataSource).bind<RemoteTaskDataSource>()

    singleOf(::KtorReminderRemoteDataSource).bind<RemoteReminderDataSource>()

    singleOf(::KtorAgendaRemoteDataSource).bind<AgendaRemoteDataSource>()

}