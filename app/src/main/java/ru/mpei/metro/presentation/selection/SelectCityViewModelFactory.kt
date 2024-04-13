package ru.mpei.metro.presentation.selection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mpei.metro.presentation.di.scopes.ApplicationScope
import ru.mpei.metro.domain.usecases.GetCitiesUseCase
import javax.inject.Inject

@ApplicationScope
class SelectCityViewModelFactory @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SelectCityViewModel(getCitiesUseCase) as T
    }
}
