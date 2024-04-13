package ru.mpei.metro.domain.usecases

import ru.mpei.metro.presentation.di.scopes.ApplicationScope
import ru.mpei.metro.domain.repository.CitiesRepository
import javax.inject.Inject

@ApplicationScope
class GetCityByIdUseCase @Inject constructor(
    private val citiesRepository: CitiesRepository,
) {
    suspend fun getCityById(cityId: String) = citiesRepository.getCityById(cityId)
}
