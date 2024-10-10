package com.tasky.agenda.presentation

import androidx.compose.runtime.Composable
import com.tasky.agenda.presentation.event_details.EventDetailsViewModel
import com.tasky.agenda.presentation.event_details.EventScreenRoot
import java.time.ZonedDateTime

@Composable
fun AgendaScreen() {

    EventScreenRoot(
        viewModel = EventDetailsViewModel(),
        selectedDate = ZonedDateTime.now().toInstant().toEpochMilli()
    )

}
