package ru.mpei.metro.presentation.map

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mpei.metro.domain.model.HistoryRoute
import ru.mpei.metro.domain.model.MetroGraph
import ru.mpei.metro.domain.model.Route
import ru.mpei.metro.domain.model.Station
import ru.mpei.metro.domain.usecases.GetCloseStationsUseCase
import ru.mpei.metro.domain.usecases.GetHistoryRoutesUseCase
import ru.mpei.metro.domain.usecases.GetRouteUseCase
import ru.mpei.metro.domain.usecases.InsertHistoryRoutesUseCase
import ru.mpei.metro.domain.usecases.UpdateMetroGraphUseCase
import ru.mpei.metro.presentation.map.bottomsheet.StationDirection
import ru.mpei.metro.presentation.map.model.SelectedStations
import java.time.LocalDateTime

class MapViewModel(
    private val getRouteUseCase: GetRouteUseCase,
    getHistoryRoutesUseCase: GetHistoryRoutesUseCase,
    private val insertHistoryRoutesUseCase: InsertHistoryRoutesUseCase,
    private val getCloseStationsUseCase: GetCloseStationsUseCase,
    private val updateMetroGraphUseCase: UpdateMetroGraphUseCase,
) : ViewModel() {
    private val _route: MutableLiveData<Route?> = MutableLiveData()
    val route: LiveData<Route?> = _route

    private val _selectedStations: MutableLiveData<SelectedStations> = MutableLiveData()
    val selectedStations: LiveData<SelectedStations> = _selectedStations
    private val _suggestedStations: MutableLiveData<List<Station>> = MutableLiveData()
    val suggestedStations: LiveData<List<Station>> = _suggestedStations
    private val _closeStations: MutableLiveData<List<Station>> = MutableLiveData()
    val closeStations: LiveData<List<Station>> = _closeStations

    val historyRoutes = getHistoryRoutesUseCase.getHistoryRoutes().asLiveData()

    var stationDirection: StationDirection = StationDirection.TO

    fun getRoute(metroGraph: MetroGraph, selectedStations: SelectedStations) {
        if (selectedStations.fromStation != null && selectedStations.toStation != null) {
            getRoute(metroGraph, selectedStations.fromStation, selectedStations.toStation)
        } else {
            _route.postValue(null)
        }
    }

    private fun getRoute(
        metroGraph: MetroGraph,
        fromStation: Station,
        toStation: Station,
    ) = viewModelScope.launch {
        val route = getRouteUseCase.getRoute(
            metroGraph = metroGraph,
            fromStation = fromStation,
            toStation = toStation,
        )
        _route.postValue(route)
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

    fun updateSuggestedStationsByQuery(metroGraph: MetroGraph, query: String?) {
        if (query.isNullOrEmpty()) {
            _suggestedStations.value = emptyList()
            return
        }
        val filteredStations = ArrayList<Station>()
        metroGraph.stations.forEach { station ->
            if (station.stationName.lowercase().contains(query.lowercase())) {
                filteredStations.add(station)
            }
        }
        _suggestedStations.value = filteredStations
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

    fun onLocationChanged(metroGraph: MetroGraph, location: Location) = viewModelScope.launch {
        _closeStations.postValue(
            getCloseStationsUseCase.getCloseStations(metroGraph, location)
        )
    }

    fun updateMetroGraph(cityId: String) = viewModelScope.launch {
        updateMetroGraphUseCase.updateMetroGraph(cityId)
    }
}
