package ru.mpei.metro.data.route

import ru.mpei.metro.domain.model.MetroGraph
import ru.mpei.metro.domain.model.Route
import ru.mpei.metro.domain.model.RouteNode
import ru.mpei.metro.domain.model.Station
import java.util.PriorityQueue

object RouteCalculator {
    fun MetroGraph.calculateRoutes(
        fromStation: Station,
        toStation: Station,
        k: Int = 3,
    ): List<Route> {
        val graph = buildGraph()
        val kShortestPaths = yenKShortestPaths(
            graph = graph,
            startNode = fromStation.id,
            goalNode = toStation.id,
            k,
        )
        return kShortestPaths.map { (distance, path) ->
            val routeNodes = mutableListOf<RouteNode>()
            var achieveTime = 0
            for (i in path.indices) {
                if (i > 0) {
                    achieveTime += graph.weights[Pair(path[i - 1], path[i])] ?: 0
                }
                val station = stations.find { station ->
                    station.id == path[i]
                }
                if (station != null) {
                    routeNodes.add(RouteNode(station, achieveTime))
                }
            }
            Route(routeNodes, distance)
        }
    }

    private fun dijkstra(graph: Graph, start: String, goal: String): Pair<Int, List<String>> {
        val queue = PriorityQueue(compareBy<Pair<Int, String>> { it.first })
        queue.add(Pair(0, start))
        val seen = mutableSetOf<String>()
        val minDist = mutableMapOf<String, Int>()
        val predecessors = mutableMapOf<String, String>()
        minDist[start] = 0

        while (queue.isNotEmpty()) {
            val (cost, node) = queue.poll()

            if (!seen.add(node)) {
                continue
            }

            if (node == goal) {
                val path = mutableListOf<String>()
                var step = goal
                while (step != start) {
                    path.add(step)
                    step = predecessors[step] ?: break
                }
                path.add(start)
                return Pair(cost, path.reversed())
            }

            for (nextNode in graph.edges[node] ?: emptyList()) {
                if (nextNode in seen) {
                    continue
                }
                val newCost = cost + (graph.weights[Pair(node, nextNode)] ?: 0)
                if (newCost < (minDist[nextNode] ?: Int.MAX_VALUE)) {
                    minDist[nextNode] = newCost
                    predecessors[nextNode] = node
                    queue.add(Pair(newCost, nextNode))
                }
            }
        }

        return Pair(Int.MAX_VALUE, emptyList())
    }

    private fun yenKShortestPaths(
        graph: Graph,
        startNode: String,
        goalNode: String,
        K: Int
    ): List<Pair<Int, List<String>>> {
        // Step 1: Find the shortest path from start to goal using Dijkstra's algorithm
        val (distance, shortestPath) = dijkstra(graph, startNode, goalNode)
        if (shortestPath.isEmpty()) {
            return emptyList()
        }

        val paths = mutableListOf(Pair(distance, shortestPath))
        val candidates = PriorityQueue(compareBy<Pair<Int, List<String>>> { it.first })

        for (k in 1 until K) {
            for (i in 0 until paths.last().second.size - 1) {
                val spurNode = paths.last().second[i]
                val rootPath = paths.last().second.subList(0, i + 1)

                val removedEdges = mutableListOf<Pair<Pair<String, String>, Int?>>()
                for ((_, path) in paths) {
                    if (path.size > i && path.subList(0, i + 1) == rootPath) {
                        val edge = Pair(path[i], path[i + 1])
                        val weight = graph.removeEdge(edge.first, edge.second)
                        if (weight != null) {
                            removedEdges.add(Pair(edge, weight))
                        }
                    }
                }

                val (spurDistance, spurPath) = dijkstra(graph, spurNode, goalNode)
                if (spurPath.isNotEmpty()) {
                    val totalPath = rootPath.dropLast(1) + spurPath
                    candidates.add(Pair(pathLength(graph, totalPath), totalPath))
                }

                for ((edge, weight) in removedEdges) {
                    graph.addEdgeBack(edge.first, edge.second, weight!!)
                }
            }

            if (candidates.isEmpty()) {
                break
            }

            paths.add(candidates.poll())
        }

        return paths.take(K)
    }

    private fun pathLength(graph: Graph, path: List<String>): Int {
        var length = 0
        for (i in 0 until path.size - 1) {
            length += graph.weights[Pair(path[i], path[i + 1])] ?: 0
        }
        return length
    }

    private fun MetroGraph.buildGraph(): Graph {
        val graph = Graph()
        for (edge in connections) {
            graph.addEdge(edge.fromStationId, edge.toStationId, edge.time)
        }
        return graph
    }
}
