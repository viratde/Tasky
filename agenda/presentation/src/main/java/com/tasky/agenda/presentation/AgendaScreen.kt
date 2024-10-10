package com.tasky.agenda.presentation

import androidx.compose.runtime.Composable
import com.tasky.agenda.presentation.agenda_item_details.AgendaDetailsViewModel
import com.tasky.agenda.presentation.agenda_item_details.EventScreenRoot
import java.time.ZonedDateTime

@Composable
fun AgendaScreen() {

    EventScreenRoot(
        viewModel = AgendaDetailsViewModel(),
        selectedDate = ZonedDateTime.now().toInstant().toEpochMilli()
    )

}
