package com.tasky.agenda.domain.model

sealed interface AgendaPhoto {

    val id: String

    data class RemotePhoto(
        override val id: String,
        val url: String
    ) : AgendaPhoto

    data class LocalPhoto(
        val photo: ByteArray,
        override val id: String
    ) : AgendaPhoto {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as LocalPhoto

            return photo.contentEquals(other.photo)
        }

        override fun hashCode(): Int {
            return photo.contentHashCode()
        }
    }
}