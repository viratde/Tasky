package com.tasky.agenda.data.utils

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoEntity(
    @SerialName("key") val key: String,
    @SerialName("url") val url: String
)