package ru.mpei.metro.domain.model

data class Route(
    val routeNodes: List<RouteNode>,
    val totalTime: Long,
)
