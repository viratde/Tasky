package com.tasky.agenda.presentation.event_details.model

data class EventUi(
    val id: String,
    val title: String,
    val description: String,
    val from: Long,  // This is going to be in utc
    val to: Long,// This is going to be in utc
    val remindAt: Long,
    val photos: List<ByteArray>,
)
