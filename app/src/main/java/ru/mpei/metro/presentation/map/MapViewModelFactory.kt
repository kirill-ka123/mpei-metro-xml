package ru.mpei.metro.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mpei.metro.domain.usecases.GetCloseStationsUseCase
import ru.mpei.metro.domain.usecases.GetHistoryRoutesUseCase
import ru.mpei.metro.domain.usecases.GetRouteUseCase
import ru.mpei.metro.domain.usecases.InsertHistoryRoutesUseCase
import ru.mpei.metro.domain.usecases.UpdateMetroGraphUseCase
import ru.mpei.metro.presentation.di.scopes.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class MapViewModelFactory @Inject constructor(
    private val getRouteUseCase: GetRouteUseCase,
    private val getHistoryRoutesUseCase: GetHistoryRoutesUseCase,
    private val insertHistoryRoutesUseCase: InsertHistoryRoutesUseCase,
    private val getCloseStationsUseCase: GetCloseStationsUseCase,
    private val updateMetroGraphUseCase: UpdateMetroGraphUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MapViewModel(
            getRouteUseCase,
            getHistoryRoutesUseCase,
            insertHistoryRoutesUseCase,
            getCloseStationsUseCase,
            updateMetroGraphUseCase
        ) as T
    }
}
