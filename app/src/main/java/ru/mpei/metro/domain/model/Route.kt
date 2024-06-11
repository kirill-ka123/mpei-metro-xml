package ru.mpei.metro.domain.model

data class Route(
    val routeNodes: List<RouteNode>,
    val totalTime: Int,
    val totalComfort: Double,
)

data class RouteNode(
    val station: Station,
    val achieveTime: Int,
    val achieveComfort: Double,
)
