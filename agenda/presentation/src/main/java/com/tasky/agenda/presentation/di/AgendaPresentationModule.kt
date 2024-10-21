package com.tasky.agenda.presentation.di

import com.tasky.agenda.presentation.agenda.AgendaItemsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val agendaPresentationModule = module {

    viewModelOf(::AgendaItemsViewModel)

}