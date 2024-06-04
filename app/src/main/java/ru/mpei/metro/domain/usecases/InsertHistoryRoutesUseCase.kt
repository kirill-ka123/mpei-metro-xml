package ru.mpei.metro.domain.usecases

import ru.mpei.metro.data.db.history.HistoryRouteEntity
import ru.mpei.metro.data.repository.MetroRepository
import ru.mpei.metro.domain.model.HistoryRoute
import ru.mpei.metro.presentation.di.scopes.ApplicationScope
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

const val MAX_HISTORY_ROUTES = 4

@ApplicationScope
class InsertHistoryRoutesUseCase @Inject constructor(
    private val metroRepository: MetroRepository,
) {
    suspend fun insertHistoryRoute(historyRoute: HistoryRoute) {
        metroRepository.getHistoryRoutes().collect { historyRoutes ->
            if (historyRoutes.size < MAX_HISTORY_ROUTES) {
                metroRepository.insertHistoryRoute(historyRoute.mapHistoryRoute())
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
