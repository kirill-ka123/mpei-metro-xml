package ru.mpei.metro.presentation.di

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.mpei.metro.domain.usecases.MetroGraphProvider
import ru.mpei.metro.presentation.di.scopes.ApplicationScope
import ru.mpei.metro.presentation.map.MapViewModelFactory

@Component(modules = [ApplicationModule::class])
@ApplicationScope
interface ApplicationComponent {
    fun context(): Context

    fun mapViewModelFactory(): MapViewModelFactory

    fun metroGraphProvider(): MetroGraphProvider

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}
