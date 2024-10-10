package com.tasky.agenda.presentation.agenda_item_details.components.utils

import com.tasky.agenda.presentation.R
import com.tasky.core.presentation.ui.UiText

enum class VisitorsFilterState {
    ALL,
    GOING,
    NOT_GOING
}

fun VisitorsFilterState.asUiText(): UiText {
    return when (this) {
        VisitorsFilterState.ALL -> UiText.StringResource(R.string.all)
        VisitorsFilterState.GOING -> UiText.StringResource(R.string.going)
        VisitorsFilterState.NOT_GOING -> UiText.StringResource(R.string.not_going)
    }
}