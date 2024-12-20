package com.tasky.agenda.presentation.agenda

import com.tasky.agenda.presentation.common.model.AgendaItemUi
import java.time.LocalDate
import java.time.ZonedDateTime

data class AgendaItemsState(
    val agendaItems: List<AgendaItemUi> = listOf(),
    val selectionStartDate: Long = ZonedDateTime.now().toInstant().toEpochMilli(),
    val selectedDate: Long = selectionStartDate,
    val isDateSelectorModelOpen: Boolean = false,
    val isAddAgendaItemDropDownOpen: Boolean = false,
    val isLogOutDropDownOpen: Boolean = false,
    val selectedAgendaItemUi: String? = null,
    val fullName: String? = null
)