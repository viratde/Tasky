package com.tasky.agenda.presentation

import androidx.compose.runtime.Composable
import com.tasky.agenda.presentation.agenda_item_details.AgendaDetailsViewModel
import com.tasky.agenda.presentation.agenda_item_details.AgendaItemDetailsRoot
import java.time.ZonedDateTime

@Composable
fun AgendaScreen() {

    AgendaItemDetailsRoot(
        viewModel = AgendaDetailsViewModel(),
        selectedDate = ZonedDateTime.now().toInstant().toEpochMilli()
    )

}
