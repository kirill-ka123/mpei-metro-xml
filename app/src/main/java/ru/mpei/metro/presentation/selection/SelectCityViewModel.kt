package ru.mpei.metro.presentation.selection

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import ru.mpei.metro.domain.model.City
import ru.mpei.metro.domain.usecases.GetCitiesUseCase

class SelectCityViewModel(
    private val getCitiesUseCase: GetCitiesUseCase,
) : ViewModel() {
    val allCities: LiveData<List<City>> = liveData {
        emit(getCitiesUseCase.getAllCities())
    }
}
