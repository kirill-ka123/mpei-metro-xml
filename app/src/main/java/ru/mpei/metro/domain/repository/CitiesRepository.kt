package ru.mpei.metro.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.mpei.metro.data.history.HistoryRouteEntity
import ru.mpei.metro.domain.model.City
import ru.mpei.metro.domain.model.Road
import ru.mpei.metro.domain.model.Station

interface CitiesRepository {
    suspend fun getAllCities(): List<City>

    suspend fun getCityById(id: String): City?

    suspend fun getRoute(city: City, fromStation: Station, toStation: Station): List<Road>

    fun getHistoryRoutes(): Flow<List<HistoryRouteEntity>>

    suspend fun insertHistoryRoute(historyRouteEntity: HistoryRouteEntity)
}
