package com.tasky.agenda.presentation.agenda_item_details.model

sealed interface AgendaPhoto {
    data class RemotePhoto(
        val id: String,
        val url: String
    ) : AgendaPhoto

    data class LocalPhoto(
        val photo: ByteArray
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