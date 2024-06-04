package ru.mpei.metro.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mpei.metro.common.Constants.DEFAULT_CITY_ID
import ru.mpei.metro.data.common.calculateRoute
import ru.mpei.metro.data.db.history.HistoryRouteDatabase
import ru.mpei.metro.data.db.history.HistoryRouteEntity
import ru.mpei.metro.data.network.MetroApi
import ru.mpei.metro.domain.model.MetroGraph
import ru.mpei.metro.domain.model.Station
import ru.mpei.metro.presentation.di.scopes.ApplicationScope
import javax.inject.Inject
import javax.inject.Provider

@ApplicationScope
class MetroRepository @Inject constructor(
    private val historyRouteDatabase: Provider<HistoryRouteDatabase>,
    private val metroApi: MetroApi,
) {
    suspend fun getAllCitiesIds() = withContext(Dispatchers.IO) {
        listOf(DEFAULT_CITY_ID)
    }

    suspend fun getMetroGraphFromNetwork() = withContext(Dispatchers.IO) {
        metroApi.getMetroGraph(DEFAULT_CITY_ID)
    }

    suspend fun getRoute(
        metroGraph: MetroGraph,
        fromStation: Station,
        toStation: Station
    ) = withContext(Dispatchers.IO) {
        metroGraph.calculateRoute(
            fromStation = fromStation,
            toStation = toStation,
        )
    }

    fun getHistoryRoutes() = historyRouteDatabase.get().historyRouteDao.getAllRoutes()

    suspend fun insertHistoryRoute(
        historyRouteEntity: HistoryRouteEntity
    ) = withContext(Dispatchers.IO) {
        historyRouteDatabase.get().historyRouteDao.insertRoute(historyRouteEntity)
    }
}
