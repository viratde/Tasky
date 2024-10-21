package com.tasky.agenda.network.event

import com.tasky.agenda.domain.model.AgendaPhoto
import com.tasky.agenda.domain.model.Event
import com.tasky.agenda.domain.model.TemporaryNetworkAttendee
import com.tasky.agenda.domain.repository.remote.RemoteEventDataSource
import com.tasky.agenda.network.common.dtos.AttendeeDto
import com.tasky.agenda.network.common.dtos.EventDto
import com.tasky.agenda.network.common.dtos.TemporaryNetworkAttendeeDto
import com.tasky.agenda.network.common.mappers.toEvent
import com.tasky.agenda.network.common.mappers.toTemporaryNetworkAttendee
import com.tasky.agenda.network.event.mappers.toCreateEventDto
import com.tasky.agenda.network.event.mappers.toUpdateEventDto
import com.tasky.core.data.networking.delete
import com.tasky.core.data.networking.get
import com.tasky.core.data.networking.put
import com.tasky.core.data.networking.submitFormWithBinaryData
import com.tasky.core.domain.util.DataError
import com.tasky.core.domain.util.EmptyDataResult
import com.tasky.core.domain.util.Result
import com.tasky.core.domain.util.mapData
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.content.PartData
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class KtorRemoteEventDataSource(
    private val httpClient: HttpClient
) : RemoteEventDataSource {

    override suspend fun update(
        event: Event,
        deletedPhotoKeys: List<String>
    ): Result<Event, DataError.Network> {
        val updateEventJson = Json.encodeToString(event.toUpdateEventDto(deletedPhotoKeys))
        return httpClient.submitFormWithBinaryData<List<PartData>, EventDto>(
            route = "/event",
            body = formData {
                event.photos.filterIsInstance<AgendaPhoto.LocalPhoto>()
                    .forEachIndexed { index, photo ->
                        append("photo$index", photo.photo, Headers.build {
                            append(HttpHeaders.ContentType, "image/jpeg")
                            append(
                                HttpHeaders.ContentDisposition,
                                "filename=${event.id}_picture$index.jpg"
                            )
                        })
                    }
                append("update_event_request", updateEventJson, Headers.build {
                    append(HttpHeaders.ContentType, "text/plain")
                    append(
                        HttpHeaders.ContentDisposition,
                        "form-data; name=\"update_event_request\""
                    )
                })
            }
        ) {
            method = HttpMethod.Put
        }.mapData { it.toEvent() }
    }

    override suspend fun create(event: Event): Result<Event, DataError.Network> {
        val createEventJson = Json.encodeToString(event.toCreateEventDto())
        return httpClient.submitFormWithBinaryData<List<PartData>, EventDto>(
            route = "/event",
            body = formData {
                event.photos.filterIsInstance<AgendaPhoto.LocalPhoto>()
                    .forEachIndexed { index, photo ->
                        append("photo$index", photo.photo, Headers.build {
                            append(HttpHeaders.ContentType, "image/jpeg")
                            append(
                                HttpHeaders.ContentDisposition,
                                "filename=${event.id}_picture$index.jpg"
                            )
                        })
                    }
                append("create_event_request", createEventJson, Headers.build {
                    append(HttpHeaders.ContentType, "text/plain")
                    append(
                        HttpHeaders.ContentDisposition,
                        "form-data; name=\"create_event_request\""
                    )
                })
            }
        ) {
            method = HttpMethod.Post
        }.mapData { it.toEvent() }
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

    override suspend fun getAttendee(email: String): Result<TemporaryNetworkAttendee?, DataError.Network> {
        return httpClient.get<TemporaryNetworkAttendeeDto>(
            route = "/attendee",
            queryParameters = mapOf(
                "email" to email
            )
        ).mapData { it.attendee?.toTemporaryNetworkAttendee() }
    }

    override suspend fun deleteLocalAttendeeFromAnEvent(eventId: String): EmptyDataResult<DataError.Network> {
        return httpClient.delete(
            route = "/attendee",
            queryParameters = mapOf(
                "eventId" to eventId
            )
        )
    }

}