package ru.mpei.metro.data.common

import ru.mpei.metro.domain.model.MetroGraph
import ru.mpei.metro.domain.model.Route
import ru.mpei.metro.domain.model.RouteNode
import ru.mpei.metro.domain.model.Station
import java.util.LinkedList
import java.util.PriorityQueue

fun MetroGraph.calculateRoute(fromStation: Station, toStation: Station) =
    dijkstra(asDijkstraGraph(), fromStation, toStation)

fun dijkstra(
    graph: Map<Station, Map<Station, Long>>,
    startStation: Station,
    targetStation: Station,
): Route {
    val distances = graph.mapValues { Long.MAX_VALUE }.toMutableMap()
    val previous: MutableMap<Station, Station?> = graph.mapValues { null }.toMutableMap()

    distances[startStation] = 0

    val stationsQueue = PriorityQueue<Pair<Station, Long>>(
        compareBy { it.second }
    ).apply { add(Pair(startStation, 0)) }

    while (stationsQueue.isNotEmpty()) {
        val (currentStation, currentDistance) = stationsQueue.poll() ?: continue

        if (currentDistance > (distances[currentStation] ?: Long.MAX_VALUE))
            continue

        if (currentStation == targetStation)
            break

        val currentStationConnections = graph[currentStation] ?: continue
        for ((neighbor, distance) in currentStationConnections) {
            val newDistance = currentDistance + distance

            if (newDistance < (distances[neighbor] ?: Long.MIN_VALUE)) {
                distances[neighbor] = newDistance
                previous[neighbor] = currentStation
                stationsQueue.add(Pair(neighbor, newDistance))
            }
        }
    }

    if (previous[targetStation] == null && startStation != targetStation) {
        throw IllegalArgumentException("No path")
    } else {
        val result = LinkedList<RouteNode>()
        var station = targetStation

        while (station != startStation) {
            result.add(RouteNode(station, distances[station] ?: 0))
            station = previous[station] ?: startStation
        }

        result.add(RouteNode(startStation, 0))

        return Route(
            routeNodes = result.reversed(),
            totalTime = result.fold(
                initial = 0,
                operation = { current, node ->
                    current + node.achieveTime
                }
            )
        )
    }
}

fun MetroGraph.asDijkstraGraph() = stations.associateWith { station ->
    val connections = HashMap<Station, Long>()
//    station.roadConnections.forEach { road ->
//        getStationById(road.stationId)?.let {
//            connections[it] = road.time
//        }
//    }
    connections
}
