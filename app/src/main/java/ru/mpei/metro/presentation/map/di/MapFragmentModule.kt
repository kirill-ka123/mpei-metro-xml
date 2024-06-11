package ru.mpei.metro.presentation.map.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import ru.mpei.metro.presentation.common.FragmentOnCreateViewListener
import ru.mpei.metro.presentation.map.BranchSplineConstructor
import ru.mpei.metro.presentation.map.bottomsheet.MetroBottomSheetController
import ru.mpei.metro.presentation.map.bottomsheet.detail.DetailBottomSheetController
import ru.mpei.metro.presentation.map.location.LocationController

@Module
interface MapFragmentModule {
    @Binds
    @IntoSet
    fun bindMetroBottomSheetController(i: MetroBottomSheetController): FragmentOnCreateViewListener

    @Binds
    @IntoSet
    fun bindDetailBottomSheetController(i: DetailBottomSheetController): FragmentOnCreateViewListener

    @Binds
    @IntoSet
    fun bindLocationController(i: LocationController): FragmentOnCreateViewListener

    @Binds
    @IntoSet
    fun bindBranchSplineConstructor(i: BranchSplineConstructor): FragmentOnCreateViewListener
}
