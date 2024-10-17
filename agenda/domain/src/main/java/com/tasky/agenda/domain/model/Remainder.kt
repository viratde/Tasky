package com.tasky.agenda.domain.model

data class Remainder(
    val id: String,
    val title: String,
    val description: String,
    val remindAt: Long,
    val time:Long
)
