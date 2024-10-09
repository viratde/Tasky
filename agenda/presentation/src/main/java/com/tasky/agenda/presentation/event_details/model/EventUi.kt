package com.tasky.agenda.presentation.event_details.model

import com.tasky.agenda.presentation.common.RemindTimes
import java.time.ZonedDateTime

data class EventUi(
    val id: String? = null,
    val title: String,
    val description: String,
    val from: Long,
    val to: Long,
    val remindAt: RemindTimes,
    val photos: List<ByteArray>
)


val FakeEventUi = EventUi(
    id = null,
    title = "New Task",
    description = "This is a test description and it will be removed in the future.",
    from = ZonedDateTime.now().toInstant().toEpochMilli(),
    to = ZonedDateTime.now().plusDays(1L).toInstant().toEpochMilli(),
    photos = listOf(),
    remindAt = RemindTimes.ONE_DAY,
)