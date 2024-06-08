package ru.mpei.metro.domain.model

data class Route(
    val routeNodes: List<RouteNode>,
    val totalTime: Int,
    val comfort: Int = 10,
)

data class RouteNode(
    val station: Station,
    val achieveTime: Int,
)
