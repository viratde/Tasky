package com.tasky.agenda.domain.utils

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observe(): Flow<Boolean>

}