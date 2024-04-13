package ru.mpei.metro.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.mpei.metro.data.history.HistoryRouteEntity
import ru.mpei.metro.presentation.di.scopes.ApplicationScope
import ru.mpei.metro.domain.model.HistoryRoute
import ru.mpei.metro.domain.repository.CitiesRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@ApplicationScope
class GetHistoryRoutesUseCase @Inject constructor(
    private val citiesRepository: CitiesRepository,
) {
    fun getHistoryRoutes(): Flow<List<HistoryRoute>> {
        val historyRoutesFlow = citiesRepository.getHistoryRoutes()
        return historyRoutesFlow.map { historyRoutes ->
            historyRoutes.map { historyRoute ->
                historyRoute.mapHistoryRoutes()
            }
        }
    }

    private fun HistoryRouteEntity.mapHistoryRoutes(): HistoryRoute {
        val pattern = "yyyy-MM-dd HH:mm:ss"
        val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
        val localeDateTime = LocalDateTime.parse(timestamp, formatter)
        return HistoryRoute(
            fromStation = fromStation,
            toStation = toStation,
            date = localeDateTime,
        )
    }
}