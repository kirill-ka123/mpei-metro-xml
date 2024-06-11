package ru.mpei.metro.presentation.metro

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mpei.metro.domain.model.HistoryRoute
import ru.mpei.metro.domain.model.MetroGraph
import ru.mpei.metro.domain.model.Route
import ru.mpei.metro.domain.model.Station
import ru.mpei.metro.domain.search.StationsSearchController
import ru.mpei.metro.domain.usecases.GetCloseStationsUseCase
import ru.mpei.metro.domain.usecases.GetHistoryRoutesUseCase
import ru.mpei.metro.domain.usecases.GetRoutesUseCase
import ru.mpei.metro.domain.usecases.InsertHistoryRoutesUseCase
import ru.mpei.metro.domain.usecases.UpdateMetroGraphUseCase
import ru.mpei.metro.presentation.metro.model.SelectedStations
import java.time.LocalDateTime

class MetroViewModel(
    private val getRoutesUseCase: GetRoutesUseCase,
    getHistoryRoutesUseCase: GetHistoryRoutesUseCase,
    private val insertHistoryRoutesUseCase: InsertHistoryRoutesUseCase,
    private val getCloseStationsUseCase: GetCloseStationsUseCase,
    private val updateMetroGraphUseCase: UpdateMetroGraphUseCase,
    private val stationsSearchController: StationsSearchController,
) : ViewModel() {
    private val _selectedRoute: MutableLiveData<Route?> = MutableLiveData()
    val selectedRoute: LiveData<Route?> = _selectedRoute
    private val _routes: MutableLiveData<List<Route>?> = MutableLiveData()
    val routes: LiveData<List<Route>?> = _routes

    private val _selectedStations: MutableLiveData<SelectedStations> = MutableLiveData()
    val selectedStations: LiveData<SelectedStations> = _selectedStations
    private val _suggestedStations: MutableLiveData<List<Station>> = MutableLiveData()
    val suggestedStations: LiveData<List<Station>> = _suggestedStations
    private val _closeStations: MutableLiveData<List<Station>> = MutableLiveData()
    val closeStations: LiveData<List<Station>> = _closeStations
    private val _selectedComfortWeight: MutableLiveData<Float> = MutableLiveData()
    val selectedComfortWeight: LiveData<Float> = _selectedComfortWeight

    val historyRoutes = getHistoryRoutesUseCase.getHistoryRoutes()

    fun getRoutes(metroGraph: MetroGraph) {
        val comfortWeight = selectedComfortWeight.value ?: 0f
        val timeWeight = 1 - comfortWeight
        val fromStation = selectedStations.value?.fromStation
        val toStation = selectedStations.value?.toStation
        if (fromStation != null && toStation != null) {
            getRoutes(metroGraph, fromStation, toStation, timeWeight, comfortWeight)
        } else {
            _routes.postValue(null)
            _selectedRoute.postValue(null)
        }
    }

    private fun getRoutes(
        metroGraph: MetroGraph,
        fromStation: Station,
        toStation: Station,
        timeWeight: Float,
        comfortWeight: Float,
    ) = viewModelScope.launch {
        val routes = getRoutesUseCase.getRoutes(
            metroGraph = metroGraph,
            fromStation = fromStation,
            toStation = toStation,
            timeWeight = timeWeight,
            comfortWeight = comfortWeight,
        )
        _routes.postValue(routes)
        _selectedRoute.postValue(routes.first())
    }

    fun setSelectedRoute(route: Route?) {
        _selectedRoute.value = route
    }

    fun insertHistoryRoute(selectedStations: SelectedStations) {
        if (selectedStations.fromStation != null && selectedStations.toStation != null) {
            insertHistoryRoute(
                HistoryRoute(
                    fromStation = selectedStations.fromStation,
                    toStation = selectedStations.toStation,
                    date = LocalDateTime.now(),
                )
            )
        }
    }

    private fun insertHistoryRoute(historyRoute: HistoryRoute) = viewModelScope.launch {
        insertHistoryRoutesUseCase.insertHistoryRoute(historyRoute)
    }

    fun updateSuggestedStationsByQuery(query: String?) =
        viewModelScope.launch {
            if (query.isNullOrEmpty()) {
                _suggestedStations.postValue(emptyList())
            } else {
                _suggestedStations.postValue(stationsSearchController.search(query))
            }
        }

    fun setSelectedStations(
        fromStation: Station?,
        toStation: Station?,
    ) {
        val currentSelectedStations = selectedStations.value ?: SelectedStations()
        val newSelectedStations = currentSelectedStations.copy(
            fromStation = fromStation,
            toStation = toStation,
        )
        _selectedStations.value = newSelectedStations
    }

    fun updateSelectedStations(
        fromStation: Station? = null,
        toStation: Station? = null,
    ) {
        val currentSelectedStations = selectedStations.value ?: SelectedStations()
        val newSelectedStations = if (fromStation != null && toStation != null) {
            currentSelectedStations.copy(
                fromStation = fromStation,
                toStation = toStation,
            )
        } else if (toStation != null) {
            currentSelectedStations.copy(toStation = toStation)
        } else if (fromStation != null) {
            currentSelectedStations.copy(fromStation = fromStation)
        } else {
            currentSelectedStations
        }
        _selectedStations.value = newSelectedStations
    }

    fun setSelectedComfortWeight(comfortWeight: Float) {
        _selectedComfortWeight.value = comfortWeight
    }

    fun onLocationChanged(metroGraph: MetroGraph, location: Location) = viewModelScope.launch {
        _closeStations.postValue(
            getCloseStationsUseCase.getCloseStations(metroGraph, location)
        )
    }

    fun updateMetroGraph(cityId: String) = viewModelScope.launch {
        //updateMetroGraphUseCase.updateMetroGraph(cityId)
    }
}
