package ru.mpei.metro.domain.model

import java.time.LocalDateTime

data class HistoryRoute(
    val fromStation: Station,
    val toStation: Station,
    val date: LocalDateTime,
)
