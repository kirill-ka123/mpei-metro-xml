package ru.mpei.metro.data.search

import ru.mpei.metro.domain.model.Station
import javax.inject.Inject

class StationsPrefixTrie @Inject constructor() {
    private var root = PrefixTrieNode()

    fun search(prefix: String): List<Station> {
        val results = mutableListOf<Station>()
        var node = root
        prefix.lowercase().forEach {
            node = node.children[it] ?: return emptyList()
        }
        collectAllStations(node, results)
        return results
    }

    private fun collectAllStations(node: PrefixTrieNode, results: MutableList<Station>) {
        node.station?.let { results.add(it) }
        node.children.forEach { (_, childNode) ->
            collectAllStations(childNode, results)
        }
    }

    fun updateTrie(stations: List<Station>) {
        root = PrefixTrieNode()

        stations.forEach { station ->
            var node = root
            station.stationName.lowercase().forEach {
                if (!node.children.containsKey(it)) {
                    node.children[it] = PrefixTrieNode()
                }
                node = node.children[it]!!
            }
            node.station = station
        }
    }

    private data class PrefixTrieNode(
        val children: MutableMap<Char, PrefixTrieNode> = mutableMapOf(),
        var station: Station? = null,
    )
}