package ru.mpei.metro.data.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mpei.metro.common.Constants.DEFAULT_CITY_ID
import ru.mpei.metro.data.db.history.HistoryRouteDatabase
import ru.mpei.metro.data.db.history.HistoryRouteEntity
import ru.mpei.metro.data.network.MetroApi
import ru.mpei.metro.data.route.findRoute
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

    suspend fun getRoutes(
        metroGraph: MetroGraph,
        fromStation: Station,
        toStation: Station,
        timeWeight: Float,
        comfortWeight: Float,
    ) = withContext(Dispatchers.IO) {
        listOf(
            metroGraph.findRoute(
                fromStation = fromStation,
                toStation = toStation,
                timeWeight = timeWeight,
                comfortWeight = comfortWeight,
            )
        )
    }

    fun getHistoryRoutes(): LiveData<List<HistoryRouteEntity>> =
        historyRouteDatabase.get().historyRouteDao.getAllRoutes()

    suspend fun insertHistoryRoute(
        historyRouteEntity: HistoryRouteEntity
    ) = withContext(Dispatchers.IO) {
        historyRouteDatabase.get().historyRouteDao.insertRoute(historyRouteEntity)
    }

    suspend fun deleteOldestHistoryRoute() = withContext(Dispatchers.IO) {
        historyRouteDatabase.get().historyRouteDao.deleteOldestRoute()
    }
}
