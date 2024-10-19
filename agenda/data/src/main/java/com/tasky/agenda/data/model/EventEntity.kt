package com.tasky.agenda.data.model

import androidx.room.Entity
import com.tasky.agenda.data.utils.AttendeeEntity
import com.tasky.agenda.data.utils.PhotoEntity

@Entity(
    tableName = "events"
)
data class EventEntity(
    val id: String,
    val title: String,
    val description: String,
    val remindAt: Long,
    val from: Long,
    val to: Long,
    val photos: List<PhotoEntity>,
    val attendees: List<AttendeeEntity>,
    val isUserEventCreator: Boolean,
    val host: String
)
