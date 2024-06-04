package ru.mpei.metro.domain.usecases

import ru.mpei.metro.data.repository.MetroRepository
import ru.mpei.metro.presentation.di.scopes.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class GetCitiesIdsUseCase @Inject constructor(
    private val metroRepository: MetroRepository,
) {
    suspend fun getAllCitiesIds() = metroRepository.getAllCitiesIds()
}
