package ru.mpei.metro.presentation.map.di

import android.app.Activity
import android.content.Context
import android.view.View
import dagger.BindsInstance
import dagger.Component
import ru.mpei.metro.domain.model.City
import ru.mpei.metro.presentation.common.FragmentOnCreateViewListener
import ru.mpei.metro.presentation.di.ActivityComponent
import ru.mpei.metro.presentation.map.MapViewModel
import javax.inject.Named

@Component(
    modules = [MapFragmentModule::class],
    dependencies = [ActivityComponent::class],
)
@MapFragmentScope
interface MapFragmentComponent {
    fun context(): Context

    fun activity(): Activity

    fun onCreateViewListeners(): Set<FragmentOnCreateViewListener>

    @Component.Builder
    interface Builder {
        fun activityComponent(activityComponent: ActivityComponent): Builder

        @BindsInstance
        fun rootView(@Named(MapFragmentConstants.MAP_FRAGMENT_ROOT_VIEW) rootView: View): Builder

        @BindsInstance
        fun mapViewModel(mapViewModel: MapViewModel): Builder

        @BindsInstance
        fun city(city: City): Builder

        fun build(): MapFragmentComponent
    }
}
