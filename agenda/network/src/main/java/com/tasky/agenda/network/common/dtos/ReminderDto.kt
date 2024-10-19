package com.tasky.agenda.network.common.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ReminderDto(
    val id: String,
    val title: String,
    val description: String,
    val remindAt: Long,
    val time:Long
)
