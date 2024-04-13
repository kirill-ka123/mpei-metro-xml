package ru.mpei.metro.domain.model

fun City.getStationById(stationId: String) = stations.find { it.id == stationId }
