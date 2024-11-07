package com.tasky.agenda.presentation.common.model


sealed interface AgendaItemUi {

    data class Item(val item: AgendaItem) : AgendaItemUi

    data object Needle : AgendaItemUi

}

