package ru.mpei.metro.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mpei.metro.presentation.di.scopes.ApplicationScope
import ru.mpei.metro.domain.usecases.GetCityByIdUseCase
import ru.mpei.metro.domain.usecases.GetHistoryRoutesUseCase
import ru.mpei.metro.domain.usecases.GetRouteUseCase
import ru.mpei.metro.domain.usecases.InsertHistoryRoutesUseCase
import javax.inject.Inject

@ApplicationScope
class MapViewModelFactory @Inject constructor(
    private val getRouteUseCase: GetRouteUseCase,
    private val getHistoryRoutesUseCase: GetHistoryRoutesUseCase,
    private val insertHistoryRoutesUseCase: InsertHistoryRoutesUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MapViewModel(
            getRouteUseCase,
            getHistoryRoutesUseCase,
            insertHistoryRoutesUseCase
        ) as T
    }
}
