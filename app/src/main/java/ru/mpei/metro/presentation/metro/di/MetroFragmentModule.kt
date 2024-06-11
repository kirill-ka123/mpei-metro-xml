package ru.mpei.metro.presentation.metro.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import ru.mpei.metro.presentation.common.FragmentOnCreateViewListener
import ru.mpei.metro.presentation.metro.BranchSplineConstructor
import ru.mpei.metro.presentation.metro.bottomsheet.MetroBottomSheetController
import ru.mpei.metro.presentation.metro.bottomsheet.detail.DetailBottomSheetController
import ru.mpei.metro.presentation.metro.location.LocationController

@Module
interface MetroFragmentModule {
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
