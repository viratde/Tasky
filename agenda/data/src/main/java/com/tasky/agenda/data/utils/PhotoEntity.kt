package com.tasky.agenda.data.utils

import kotlinx.serialization.Serializable

@Serializable
data class PhotoEntity(
    val key: String,
    val url: String
)