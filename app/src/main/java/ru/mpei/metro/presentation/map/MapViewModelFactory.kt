package ru.mpei.metro.presentation.map

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mpei.metro.domain.usecases.GetCloseStationsUseCase
import ru.mpei.metro.domain.usecases.GetHistoryRoutesUseCase
import ru.mpei.metro.domain.usecases.GetRouteUseCase
import ru.mpei.metro.domain.usecases.InsertHistoryRoutesUseCase
import ru.mpei.metro.presentation.di.scopes.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class MapViewModelFactory @Inject constructor(
    private val getRouteUseCase: GetRouteUseCase,
    private val getHistoryRoutesUseCase: GetHistoryRoutesUseCase,
    private val insertHistoryRoutesUseCase: InsertHistoryRoutesUseCase,
    private val getCloseStationsUseCase: GetCloseStationsUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MapViewModel(
            getRouteUseCase,
            getHistoryRoutesUseCase,
            insertHistoryRoutesUseCase,
            getCloseStationsUseCase
        ) as T
    }
}
