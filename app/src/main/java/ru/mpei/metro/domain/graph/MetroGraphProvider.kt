package ru.mpei.metro.domain.graph

import ru.mpei.metro.common.Constants
import ru.mpei.metro.common.observer.Observable
import ru.mpei.metro.common.observer.ObservableDelegate
import ru.mpei.metro.data.cache.InMemoryCaching
import ru.mpei.metro.data.repository.CachingRepository
import ru.mpei.metro.domain.model.MetroGraph
import ru.mpei.metro.presentation.di.scopes.ApplicationScope
import java.io.InputStream
import javax.inject.Inject

fun interface MetroGraphUpdateListener {
    fun onMetroGraphUpdated(metroGraph: MetroGraph)
}

@ApplicationScope
class MetroGraphProvider @Inject constructor(
    private val cachingRepository: CachingRepository,
    inMemoryCaching: InMemoryCaching,
    private val metroGraphParser: MetroGraphParser,
    private val observableDelegate: ObservableDelegate<MetroGraphUpdateListener>,
) : Observable<MetroGraphUpdateListener> by observableDelegate {
    init {
        inMemoryCaching.addObserver {
            observableDelegate.notify { onMetroGraphUpdated(getMetroGraph()) }
        }
    }

    fun getMetroGraph(cityId: String = Constants.DEFAULT_CITY_ID): MetroGraph {
        val decoder = { inputStream: InputStream -> metroGraphParser.fromJson(inputStream) }
        return cachingRepository.load(
            key = cityId,
            decoder = decoder
        ).resource
    }
}
