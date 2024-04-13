package ru.mpei.metro.domain.states

import ru.mpei.metro.domain.model.Station

data class SelectedStations(
    val fromStation: Station? = null,
    val toStation: Station? = null,
)
