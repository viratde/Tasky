package com.tasky.agenda.network.event

import com.tasky.agenda.domain.model.AgendaPhoto
import com.tasky.agenda.domain.model.Event
import com.tasky.agenda.domain.repository.remote.RemoteEventDataSource
import com.tasky.agenda.network.common.dtos.EventDto
import com.tasky.agenda.network.common.mappers.toEvent
import com.tasky.core.data.networking.delete
import com.tasky.core.data.networking.get
import com.tasky.core.data.networking.post
import com.tasky.core.data.networking.put
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import com.tasky.core.domain.util.Result
import com.tasky.core.domain.util.mapData
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData

class KtorRemoteEventDataSource(
    private val httpClient: HttpClient
) : RemoteEventDataSource {

    override suspend fun update(
        event: Event,
        deletedPhotoKeys: List<String>
    ): Result<Event, DataError.Network> {
        return httpClient.put<MultiPartFormDataContent, EventDto>(
            route = "/event",
            body = MultiPartFormDataContent(
                formData {
                    append("id", event.id)
                    append("title", event.title)
                    append("description", event.description)
                    append("from", event.from)
                    append("to", event.to)
                    append("remindAt", event.remindAt)
                    append("attendeeIds", event.attendees.map { it.userId })
                    append("deletedPhotoKeys", deletedPhotoKeys)
                    event.photos.filterIsInstance<AgendaPhoto.LocalPhoto>()
                        .mapIndexed { index, photo ->
                            append("photo$index", photo.photo)
                        }
                }
            )
        ).mapData { it.toEvent() }
    }

    override suspend fun create(event: Event): Result<Event, DataError.Network> {
        return httpClient.post<MultiPartFormDataContent, EventDto>(
            route = "/event",
            body = MultiPartFormDataContent(
                formData {
                    append("id", event.id)
                    append("title", event.title)
                    append("description", event.description)
                    append("from", event.from)
                    append("to", event.to)
                    append("remindAt", event.remindAt)
                    append("attendeeIds", event.attendees.map { it.userId })
                    event.photos.filterIsInstance<AgendaPhoto.LocalPhoto>()
                        .mapIndexed { index, photo ->
                            append("photo$index", photo.photo)
                        }
                }
            )
        ).mapData { it.toEvent() }
    }

    override suspend fun delete(eventId: String): EmptyDataResult<DataError.Network> {
        return httpClient.delete(
            route = "/event",
            queryParameters = mapOf(
                "eventId" to eventId
            )
        )
    }

    override suspend fun get(eventId: String): Result<Event, DataError.Network> {
        return httpClient.get<EventDto>(
            route = "/event",
            queryParameters = mapOf(
                "eventId" to eventId
            )
        ).mapData { it.toEvent() }
    }

}