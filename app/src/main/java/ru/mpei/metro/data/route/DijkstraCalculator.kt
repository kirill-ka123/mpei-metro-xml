package ru.mpei.metro.data.route

import ru.mpei.metro.domain.model.Connection
import ru.mpei.metro.domain.model.MetroGraph
import ru.mpei.metro.domain.model.Route
import ru.mpei.metro.domain.model.RouteNode
import ru.mpei.metro.domain.model.Station

fun MetroGraph.findRoute(
    fromStation: Station,
    toStation: Station,
    timeWeight: Float,
    comfortWeight: Float,
): Route {
    val graph = buildGraph(connections)
    val distances = mutableMapOf<String, Int>()
    val previous = mutableMapOf<String, Connection>()
    val queue = mutableListOf<Pair<String, Int>>()

    distances[fromStation.id] = 0
    queue.add(Pair(fromStation.id, 0))

    while (queue.isNotEmpty()) {
        val (currentStationId, currentDistance) = queue.removeAt(0)

        if (currentStationId == toStation.id) {
            break
        }

        val currentConnections = graph[currentStationId] ?: emptyList()

        for (connection in currentConnections) {
            val neighborStationId = connection.toStationId
            val newDistance = currentDistance + calculateCost(connection, timeWeight, comfortWeight)

            if (distances.getOrDefault(neighborStationId, Int.MAX_VALUE) > newDistance) {
                distances[neighborStationId] = newDistance
                previous[neighborStationId] = connection
                queue.add(Pair(neighborStationId, newDistance))
            }
        }

        queue.sortBy { it.second }
    }

    return buildRoute(previous, fromStation, toStation)
}

private fun buildGraph(connections: List<Connection>): Map<String, List<Connection>> {
    val graph = mutableMapOf<String, MutableList<Connection>>()

    for (connection in connections) {
        graph.getOrPut(connection.fromStationId) { mutableListOf() }.add(connection)
    }

    return graph
}

private fun calculateCost(connection: Connection, weightTime: Float, weightComfort: Float): Int {
    return (connection.time * weightTime + (1 - connection.comfort) * connection.time * weightComfort).toInt()
}

private fun MetroGraph.buildRoute(
    previous: Map<String, Connection>,
    fromStation: Station,
    toStation: Station,
): Route {
    val routeNodes = mutableListOf<RouteNode>()
    var currentStation = toStation
    var totalTime = 0
    var totalComfort = 0.0

    while (currentStation != fromStation) {
        val connection = previous[currentStation.id] ?: break
        val previousStation = stations.find { it.id == connection.fromStationId } ?: break
        routeNodes.add(RouteNode(currentStation, totalTime, totalComfort))
        totalTime += connection.time
        totalComfort += connection.comfort * connection.time
        currentStation = previousStation
    }

    routeNodes.add(RouteNode(fromStation, totalTime, totalComfort))
    routeNodes.reverse()

    val comfort = totalComfort / totalTime

    return Route(routeNodes, totalTime, comfort)
}
