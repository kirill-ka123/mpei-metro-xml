package ru.mpei.metro.domain.usecases

import android.location.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mpei.metro.domain.model.MetroGraph
import ru.mpei.metro.presentation.di.scopes.ApplicationScope
import javax.inject.Inject

private const val MAX_DISTANCE = 10_000F

@ApplicationScope
class GetCloseStationsUseCase @Inject constructor() {
    suspend fun getCloseStations(metroGraph: MetroGraph, location: Location) = withContext(Dispatchers.IO) {
        metroGraph.stations.mapNotNull { station ->
            val stationLocation = Location("").apply {
                latitude = station.lat.toDouble()
                longitude = station.lon.toDouble()
            }
            val distance = location.distanceTo(stationLocation)
            (station to distance).takeIf { distance < MAX_DISTANCE }
        }.sortedBy { it.second }.map { it.first }
    }
}
