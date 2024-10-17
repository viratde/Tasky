package com.tasky.agenda.domain.repository.remote

import com.tasky.agenda.domain.model.Event
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import com.tasky.core.domain.util.Result

interface RemoteEventRepository {

    suspend fun update(event: Event): Result<Event, DataError.Network>

    suspend fun create(event: Event): Result<Event, DataError.Network>

    suspend fun delete(
        eventId: String,
        deletedPhotoKeys: List<String>
    ): EmptyDataResult<DataError.Network>

    suspend fun get(eventId: String): Result<Event, DataError.Network>

}