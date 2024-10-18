package com.tasky.agenda.network.common.dtos

import kotlinx.serialization.Serializable

@Serializable
data class TaskDto(
    val id:String,
    val title:String,
    val description:String,
    val time:Long,
    val remindAt:Long,
    val isDone:Boolean
)