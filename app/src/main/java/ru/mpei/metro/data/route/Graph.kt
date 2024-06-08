package ru.mpei.metro.data.route

class Graph {
    val edges = mutableMapOf<String, MutableList<String>>()
    val weights = mutableMapOf<Pair<String, String>, Int>()

    fun addEdge(fromNode: String, toNode: String, weight: Int) {
        edges.computeIfAbsent(fromNode) { mutableListOf() }.add(toNode)
        weights[Pair(fromNode, toNode)] = weight
    }

    fun removeEdge(fromNode: String, toNode: String): Int? {
        edges[fromNode]?.remove(toNode)
        return weights.remove(Pair(fromNode, toNode))
    }

    fun addEdgeBack(fromNode: String, toNode: String, weight: Int) {
        edges.computeIfAbsent(fromNode) { mutableListOf() }.add(toNode)
        weights[Pair(fromNode, toNode)] = weight
    }
}