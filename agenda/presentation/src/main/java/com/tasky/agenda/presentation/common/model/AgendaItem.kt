package com.tasky.agenda.presentation.common.model

import com.tasky.agenda.domain.model.AgendaPhoto
import com.tasky.agenda.domain.model.Attendee
import com.tasky.agenda.domain.model.FakeAttendee
import com.tasky.agenda.presentation.agenda_item_details.components.utils.RemindTimes
import java.time.ZonedDateTime
import java.util.UUID

sealed interface AgendaItem {

    val id: String
    val title: String
    val description: String
    val remindAt: RemindTimes


    data class EventUi(
        override val id: String,
        override val title: String,
        override val description: String,
        val from: Long,
        val to: Long,
        override val remindAt: RemindTimes,
        val photos: List<AgendaPhoto>,
        val attendees: List<Attendee>,
        val isHost: Boolean,
        val hostId:String
    ) : AgendaItem

    data class TaskUi(
        override val id: String,
        override val title: String,
        override val description: String,
        val time: Long,
        override val remindAt: RemindTimes,
        val isDone: Boolean
    ) : AgendaItem

    data class ReminderUi(
        override val id: String,
        override val title: String,
        override val description: String,
        val time: Long,
        override val remindAt: RemindTimes,
    ) : AgendaItem

    fun copy(
        id: String = this.id,
        title: String = this.title,
        description: String = this.description,
        remindAt: RemindTimes = this.remindAt,
    ): AgendaItem {
        return when (this) {
            is EventUi -> EventUi(
                id = id,
                title = title,
                description = description,
                remindAt = remindAt,
                to = to,
                from = from,
                photos = photos,
                attendees = attendees,
                isHost = isHost,
                hostId = hostId
            )

            is ReminderUi -> ReminderUi(
                id = id,
                title = title,
                description = description,
                remindAt = remindAt,
                time = time
            )

            is TaskUi -> TaskUi(
                id = id,
                title = title,
                description = description,
                remindAt = remindAt,
                time = time,
                isDone = isDone
            )
        }
    }
}

val FakeEventUi = AgendaItem.EventUi(
    id = UUID.randomUUID().toString(),
    title = "New Task",
    description = "This is a test description and it will be removed in the future.",
    from = ZonedDateTime.now().toInstant().toEpochMilli(),
    to = ZonedDateTime.now().plusDays(1L).toInstant().toEpochMilli(),
    photos = listOf(),
    remindAt = RemindTimes.ONE_DAY,
    isHost = false,
    hostId = "",
    attendees = listOf(
        FakeAttendee,
        FakeAttendee,
        FakeAttendee,
        FakeAttendee,
        FakeAttendee,
        FakeAttendee.copy(isGoing = false),
        FakeAttendee.copy(isGoing = false),
        FakeAttendee.copy(isGoing = false),
        FakeAttendee.copy(isGoing = false),
        FakeAttendee.copy(isGoing = false),
        FakeAttendee.copy(isGoing = false),
    )
)

val FakeRemainderUi = AgendaItem.ReminderUi(
    id = UUID.randomUUID().toString(),
    title = "New Remainder",
    description = "This is a test description and it will be removed in the future.",
    time = ZonedDateTime.now().toInstant().toEpochMilli(),
    remindAt = RemindTimes.ONE_DAY,
)

val FakeTaskUi = AgendaItem.TaskUi(
    id = UUID.randomUUID().toString(),
    title = "New Task",
    description = "This is a test description and it will be removed in the future.",
    time = ZonedDateTime.now().toInstant().toEpochMilli(),
    remindAt = RemindTimes.ONE_DAY,
    isDone = false
)