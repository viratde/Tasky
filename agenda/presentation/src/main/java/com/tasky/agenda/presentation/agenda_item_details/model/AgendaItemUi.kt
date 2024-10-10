package com.tasky.agenda.presentation.agenda_item_details.model

import com.tasky.agenda.presentation.agenda_item_details.components.utils.RemindTimes
import java.time.ZonedDateTime


sealed interface AgendaItemUi {

    val id: String?
    val title: String
    val description: String
    val remindAt: RemindTimes


    data class EventUi(
        override val id: String? = null,
        override val title: String,
        override val description: String,
        val from: Long,
        val to: Long,
        override val remindAt: RemindTimes,
        val photos: List<AgendaPhoto>,
        val attendees: List<Attendee>
    ) : AgendaItemUi

    data class TaskUi(
        override val id: String? = null,
        override val title: String,
        override val description: String,
        val time: Long,
        override val remindAt: RemindTimes,
        val isDone: Boolean
    ) : AgendaItemUi

    data class ReminderUi(
        override val id: String? = null,
        override val title: String,
        override val description: String,
        val time: Long,
        override val remindAt: RemindTimes,
    ) : AgendaItemUi

    fun copy(
        id: String? = this.id,
        title: String = this.title,
        description: String = this.description,
        remindAt: RemindTimes = this.remindAt
    ): AgendaItemUi {
        return when (this) {
            is EventUi -> EventUi(
                id = id,
                title = title,
                description = description,
                remindAt = remindAt,
                to = to,
                from = from,
                photos = photos,
                attendees = attendees
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


val FakeEventUi = AgendaItemUi.EventUi(
    id = null,
    title = "New Task",
    description = "This is a test description and it will be removed in the future.",
    from = ZonedDateTime.now().toInstant().toEpochMilli(),
    to = ZonedDateTime.now().plusDays(1L).toInstant().toEpochMilli(),
    photos = listOf(),
    remindAt = RemindTimes.ONE_DAY,
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