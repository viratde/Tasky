package com.tasky.agenda.network.common.dtos

import kotlinx.serialization.Serializable

@Serializable
data class PhotoDto(
    val key:String,
    val url:String
)
