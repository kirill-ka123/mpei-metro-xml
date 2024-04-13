package ru.mpei.metro.data

import ru.mpei.metro.domain.model.City
import ru.mpei.metro.data.model.RoadEntity
import ru.mpei.metro.domain.model.Road
import ru.mpei.metro.domain.model.Station
import ru.mpei.metro.domain.model.getStationById
import java.util.LinkedList
import java.util.PriorityQueue

fun City.calculateRoute(fromStation: Station, toStation: Station) =
    dijkstra(asDijkstraGraph(), fromStation, toStation)

fun dijkstra(
    graph: Map<Station, Map<Station, Int>>,
    startStation: Station,
    targetStation: Station,
): List<Road> {
    val distances = graph.mapValues { Int.MAX_VALUE }.toMutableMap()
    val previous: MutableMap<Station, Station?> = graph.mapValues { null }.toMutableMap()

    distances[startStation] = 0

    val stationsQueue = PriorityQueue<Pair<Station, Int>>(
        compareBy { it.second }
    ).apply { add(Pair(startStation, 0)) }

    while (stationsQueue.isNotEmpty()) {
        val (currentStation, currentDistance) = stationsQueue.poll() ?: continue

        if (currentDistance > (distances[currentStation] ?: Int.MAX_VALUE))
            continue

        if (currentStation == targetStation)
            break

        val currentStationConnections = graph[currentStation] ?: continue
        for ((neighbor, distance) in currentStationConnections) {
            val newDistance = currentDistance + distance

            if (newDistance < (distances[neighbor] ?: Int.MIN_VALUE)) {
                distances[neighbor] = newDistance
                previous[neighbor] = currentStation
                stationsQueue.add(Pair(neighbor, newDistance))
            }
        }
    }

    if (previous[targetStation] == null && startStation != targetStation) {
        throw IllegalArgumentException("No path")
    } else {
        val result = LinkedList<Road>()
        var station = targetStation

        while (station != startStation) {
            result.add(Road(station, distances[station] ?: 0))
            station = previous[station] ?: startStation
        }

        result.add(Road(startStation, 0))

        return result.reversed()
    }
}

fun City.asDijkstraGraph() = stations.associateWith { station ->
    val connections = HashMap<Station, Int>()
    station.roadConnections.forEach { road ->
        getStationById(road.stationId)?.let {
            connections[it] = road.time
        }
    }
    connections
}
