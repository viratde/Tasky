package com.tasky.agenda.presentation.agenda

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AgendaItemsViewModel : ViewModel() {

    var state by mutableStateOf(AgendaItemsState())
        private set


    fun onAction(action: AgendaItemsAction){

    }

}