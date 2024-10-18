package com.tasky.agenda.network.common.mappers

import com.tasky.agenda.domain.model.AgendaPhoto
import com.tasky.agenda.network.common.dtos.PhotoDto

fun PhotoDto.toAgendaPhoto(): AgendaPhoto {
    return AgendaPhoto.RemotePhoto(
        id = key,
        url = url
    )
}
