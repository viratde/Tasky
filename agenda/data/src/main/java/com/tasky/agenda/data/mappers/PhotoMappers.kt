package com.tasky.agenda.data.mappers

import com.tasky.agenda.data.utils.PhotoEntity
import com.tasky.agenda.domain.model.AgendaPhoto

fun PhotoEntity.toPhoto(): AgendaPhoto {
    return AgendaPhoto.RemotePhoto(
        id = key,
        url = url
    )
}

fun AgendaPhoto.RemotePhoto.toPhotoEntity(): PhotoEntity {
    return PhotoEntity(
        key = id,
        url = url
    )
}