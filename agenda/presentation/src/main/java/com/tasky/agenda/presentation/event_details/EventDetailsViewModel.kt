package com.tasky.agenda.presentation.event_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class EventDetailsViewModel :ViewModel() {

    var state by mutableStateOf(EventDetailsState())
        private set

}