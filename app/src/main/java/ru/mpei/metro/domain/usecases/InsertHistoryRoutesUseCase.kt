package ru.mpei.metro.domain.usecases

import ru.mpei.metro.data.history.HistoryRouteEntity
import ru.mpei.metro.presentation.di.scopes.ApplicationScope
import ru.mpei.metro.domain.model.HistoryRoute
import ru.mpei.metro.domain.repository.CitiesRepository
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

const val MAX_HISTORY_ROUTES = 4

@ApplicationScope
class InsertHistoryRoutesUseCase @Inject constructor(
    private val citiesRepository: CitiesRepository,
) {
    suspend fun insertHistoryRoute(historyRoute: HistoryRoute) {
        citiesRepository.getHistoryRoutes().collect { historyRoutes ->
            if (historyRoutes.size < MAX_HISTORY_ROUTES) {
                citiesRepository.insertHistoryRoute(historyRoute.mapHistoryRoute())
            }
        }
    }

    private fun HistoryRoute.mapHistoryRoute(): HistoryRouteEntity {
        val pattern = "yyyy-MM-dd HH:mm:ss"
        val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
        return HistoryRouteEntity(
            fromStation = fromStation,
            toStation = toStation,
            timestamp = date.format(formatter),
        )
    }
}
