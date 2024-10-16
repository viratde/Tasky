package com.tasky.agenda.presentation.agenda

import com.tasky.agenda.presentation.common.model.AgendaItemUi
import java.time.LocalDate

data class AgendaItemsState(
    val agendaItems: List<AgendaItemUi> = listOf(),
    val selectionStartDate:LocalDate = LocalDate.now(),
    val selectedDate: LocalDate = LocalDate.now(),
    val isDateSelectorModelOpen: Boolean = false,
    val isAddAgendaItemDropDownOpen: Boolean = false,
    val isLogOutDropDownOpen: Boolean = false,
    val selectedAgendaItemUi: AgendaItemUi? = null,
    val fullName: String = ""
)