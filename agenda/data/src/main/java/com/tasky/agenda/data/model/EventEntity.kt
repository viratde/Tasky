package com.tasky.agenda.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tasky.agenda.data.utils.AttendeeEntity
import com.tasky.agenda.data.utils.PhotoEntity

@Entity(
    tableName = "eventEntity"
)
data class EventEntity(
    @PrimaryKey val id: String,
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
