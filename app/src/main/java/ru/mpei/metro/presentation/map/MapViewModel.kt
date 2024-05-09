package ru.mpei.metro.presentation.map

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mpei.metro.domain.model.City
import ru.mpei.metro.domain.model.HistoryRoute
import ru.mpei.metro.domain.model.Road
import ru.mpei.metro.domain.model.Station
import ru.mpei.metro.domain.states.SelectedStations
import ru.mpei.metro.domain.usecases.GetCloseStationsUseCase
import ru.mpei.metro.domain.usecases.GetHistoryRoutesUseCase
import ru.mpei.metro.domain.usecases.GetRouteUseCase
import ru.mpei.metro.domain.usecases.InsertHistoryRoutesUseCase
import ru.mpei.metro.presentation.map.bottomsheet.StationDirection
import java.time.LocalDateTime

class MapViewModel(
    private val getRouteUseCase: GetRouteUseCase,
    getHistoryRoutesUseCase: GetHistoryRoutesUseCase,
    private val insertHistoryRoutesUseCase: InsertHistoryRoutesUseCase,
    private val getCloseStationsUseCase: GetCloseStationsUseCase,
) : ViewModel() {
    private val _route: MutableLiveData<List<Road>?> = MutableLiveData()
    val route: LiveData<List<Road>?> = _route

    private val _selectedStations: MutableLiveData<SelectedStations> = MutableLiveData()
    val selectedStations: LiveData<SelectedStations> = _selectedStations
    private val _suggestedStations: MutableLiveData<List<Station>> = MutableLiveData()
    val suggestedStations: LiveData<List<Station>> = _suggestedStations
    private val _closeStations: MutableLiveData<List<Station>> = MutableLiveData()
    val closeStations: LiveData<List<Station>> = _closeStations

    val historyRoutes = getHistoryRoutesUseCase.getHistoryRoutes().asLiveData()

    var stationDirection: StationDirection = StationDirection.TO

    fun getRoute(city: City, selectedStations: SelectedStations) {
        if (selectedStations.fromStation != null && selectedStations.toStation != null) {
            getRoute(city, selectedStations.fromStation, selectedStations.toStation)
        } else {
            _route.postValue(null)
        }
    }

    private fun getRoute(
        city: City,
        fromStation: Station,
        toStation: Station,
    ) = viewModelScope.launch {
        val route = getRouteUseCase.getRoute(
            city = city,
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

    fun updateSuggestedStationsByQuery(city: City, query: String?) {
        if (query.isNullOrEmpty()) {
            _suggestedStations.value = emptyList()
            return
        }
        val filteredStations = ArrayList<Station>()
        city.stations.forEach { station ->
            if (station.name.lowercase().contains(query.lowercase())) {
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

    fun onLocationChanged(city: City, location: Location) = viewModelScope.launch {
        _closeStations.postValue(
            getCloseStationsUseCase.getCloseStations(city, location)
        )
    }
}
