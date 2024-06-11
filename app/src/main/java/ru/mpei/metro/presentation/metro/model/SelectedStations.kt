package ru.mpei.metro.presentation.metro.model

import ru.mpei.metro.domain.model.Station

data class SelectedStations(
    val fromStation: Station? = null,
    val toStation: Station? = null,
)
