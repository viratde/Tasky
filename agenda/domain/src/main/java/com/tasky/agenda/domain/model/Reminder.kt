package com.tasky.agenda.domain.model

data class Reminder(
    val id: String,
    val title: String,
    val description: String,
    val remindAt: Long,
    val time:Long
)
