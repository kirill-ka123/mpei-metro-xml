package ru.mpei.metro.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mpei.metro.data.calculateRoute
import ru.mpei.metro.data.history.HistoryRouteEntity
import ru.mpei.metro.data.history.MetroDatabase
import ru.mpei.metro.data.mock.CityMock
import ru.mpei.metro.domain.model.City
import ru.mpei.metro.domain.model.Station
import ru.mpei.metro.domain.repository.CitiesRepository
import ru.mpei.metro.presentation.di.scopes.ApplicationScope
import javax.inject.Inject
import javax.inject.Provider

@ApplicationScope
class CitiesRepositoryImpl @Inject constructor(
    private val metroDatabase: Provider<MetroDatabase>,
): CitiesRepository {
    private val cities = listOf(
        CityMock.MOSCOW.value,
        CityMock.SAINT_PETERSBURG.value,
    )

    override suspend fun getAllCities() = withContext(Dispatchers.IO) {
        cities
    }

    override suspend fun getCityById(id: String) = withContext(Dispatchers.IO) {
        cities.find { it.id == id }
    }

    override suspend fun getRoute(
        city: City,
        fromStation: Station,
        toStation: Station
    ) = withContext(Dispatchers.IO) {
        city.calculateRoute(
            fromStation = fromStation,
            toStation = toStation,
        )
    }

    override fun getHistoryRoutes() = metroDatabase.get().historyRouteDao.getAllRoutes()

    override suspend fun insertHistoryRoute(
        historyRouteEntity: HistoryRouteEntity
    ) = withContext(Dispatchers.IO) {
        metroDatabase.get().historyRouteDao.insertRoute(historyRouteEntity)
    }
}
