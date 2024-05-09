package ru.mpei.metro.presentation.map.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import ru.mpei.metro.presentation.common.FragmentOnCreateViewListener
import ru.mpei.metro.presentation.map.bottomsheet.MapBottomSheetController
import ru.mpei.metro.presentation.map.bottomsheet.detail.DetailBottomSheetController
import ru.mpei.metro.presentation.map.location.LocationController

@Module
interface MapFragmentModule {
    @Binds
    @IntoSet
    fun bindMapBottomSheetController(i: MapBottomSheetController): FragmentOnCreateViewListener

    @Binds
    @IntoSet
    fun bindDetailBottomSheetController(i: DetailBottomSheetController): FragmentOnCreateViewListener

    @Binds
    @IntoSet
    fun bindLocationController(i: LocationController): FragmentOnCreateViewListener
}
