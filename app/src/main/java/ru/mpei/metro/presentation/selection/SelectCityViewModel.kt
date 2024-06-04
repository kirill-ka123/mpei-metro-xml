package ru.mpei.metro.presentation.selection

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import ru.mpei.metro.domain.usecases.GetCitiesIdsUseCase

class SelectCityViewModel(
    private val getCitiesIdsUseCase: GetCitiesIdsUseCase,
) : ViewModel() {
    val allCitiesIds: LiveData<List<String>> = liveData {
        emit(getCitiesIdsUseCase.getAllCitiesIds())
    }
}
