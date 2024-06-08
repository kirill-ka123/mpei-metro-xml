package ru.mpei.metro.domain.search

import ru.mpei.metro.data.search.StationsPrefixTrie
import ru.mpei.metro.domain.graph.MetroGraphProvider
import ru.mpei.metro.presentation.di.scopes.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class StationsSearchController @Inject constructor(
    private val metroGraphProvider: MetroGraphProvider,
    private val stationsPrefixTrie: StationsPrefixTrie,
) {
    init {
        stationsPrefixTrie.updateTrie(metroGraphProvider.getMetroGraph().stations)
        metroGraphProvider.addObserver {
            stationsPrefixTrie.updateTrie(metroGraphProvider.getMetroGraph().stations)
        }
    }

    fun search(prefix: String) = stationsPrefixTrie.search(prefix)
}
