package com.tasky.agenda.presentation.event_details.model

import com.tasky.agenda.presentation.event_details.components.RemindTimes
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
        val photos: List<ByteArray>
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
}


val FakeEventUi = AgendaItemUi.EventUi(
    id = null,
    title = "New Task",
    description = "This is a test description and it will be removed in the future.",
    from = ZonedDateTime.now().toInstant().toEpochMilli(),
    to = ZonedDateTime.now().plusDays(1L).toInstant().toEpochMilli(),
    photos = listOf(),
    remindAt = RemindTimes.ONE_DAY,
)