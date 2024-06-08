package ru.mpei.metro.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mpei.metro.domain.usecases.GetCloseStationsUseCase
import ru.mpei.metro.domain.usecases.GetHistoryRoutesUseCase
import ru.mpei.metro.domain.usecases.GetRoutesUseCase
import ru.mpei.metro.domain.usecases.InsertHistoryRoutesUseCase
import ru.mpei.metro.domain.usecases.UpdateMetroGraphUseCase
import ru.mpei.metro.presentation.di.scopes.ApplicationScope
import ru.mpei.metro.domain.search.StationsSearchController
import javax.inject.Inject

@ApplicationScope
class MapViewModelFactory @Inject constructor(
    private val getRoutesUseCase: GetRoutesUseCase,
    private val getHistoryRoutesUseCase: GetHistoryRoutesUseCase,
    private val insertHistoryRoutesUseCase: InsertHistoryRoutesUseCase,
    private val getCloseStationsUseCase: GetCloseStationsUseCase,
    private val updateMetroGraphUseCase: UpdateMetroGraphUseCase,
    private val stationsSearchController: StationsSearchController,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MapViewModel(
            getRoutesUseCase,
            getHistoryRoutesUseCase,
            insertHistoryRoutesUseCase,
            getCloseStationsUseCase,
            updateMetroGraphUseCase,
            stationsSearchController,
        ) as T
    }
}
