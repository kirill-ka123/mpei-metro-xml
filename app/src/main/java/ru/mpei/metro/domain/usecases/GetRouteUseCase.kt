package ru.mpei.metro.domain.usecases

import ru.mpei.metro.domain.model.City
import ru.mpei.metro.presentation.di.scopes.ApplicationScope
import ru.mpei.metro.domain.model.Station
import ru.mpei.metro.domain.repository.CitiesRepository
import javax.inject.Inject

@ApplicationScope
class GetRouteUseCase @Inject constructor(
    private val citiesRepository: CitiesRepository,
) {
    suspend fun getRoute(city: City, fromStation: Station, toStation: Station) =
        citiesRepository.getRoute(city, fromStation, toStation)
}
