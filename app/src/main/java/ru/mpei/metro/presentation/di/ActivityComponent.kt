package ru.mpei.metro.presentation.di

import android.app.Activity
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent
import ru.mpei.metro.presentation.di.scopes.ActivityScope
import ru.mpei.metro.presentation.map.MapViewModelFactory

@Component(
    modules = [ActivityModule::class],
    dependencies = [ApplicationComponent::class],
)
@ActivityScope
interface ActivityComponent {
    fun context(): Context

    fun activity(): Activity

    fun mapViewModelFactory(): MapViewModelFactory

    @Component.Builder
    interface Builder {
        fun applicationComponent(applicationComponent: ApplicationComponent): Builder

        @BindsInstance
        fun activity(activity: Activity): Builder

        fun build(): ActivityComponent
    }
}
