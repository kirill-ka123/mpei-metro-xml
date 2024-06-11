package ru.mpei.metro.domain.usecases

import ru.mpei.metro.data.repository.MetroRepository
import ru.mpei.metro.domain.model.MetroGraph
import ru.mpei.metro.domain.model.Station
import ru.mpei.metro.presentation.di.scopes.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class GetRoutesUseCase @Inject constructor(
    private val metroRepository: MetroRepository,
) {
    suspend fun getRoutes(
        metroGraph: MetroGraph,
        fromStation: Station,
        toStation: Station,
        timeWeight: Float,
        comfortWeight: Float,
    ) = metroRepository.getRoutes(
        metroGraph,
        fromStation,
        toStation,
        timeWeight,
        comfortWeight
    )
}
