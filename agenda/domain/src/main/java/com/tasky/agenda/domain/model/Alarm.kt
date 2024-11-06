package com.tasky.agenda.domain.model

data class Alarm(
    val id: String,
    val title: String,
    val description: String,
    val at: Long
)