package com.tasky.agenda.domain.model

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val remindAt: Long,
    val time:Long,
    val isDone:Boolean
)