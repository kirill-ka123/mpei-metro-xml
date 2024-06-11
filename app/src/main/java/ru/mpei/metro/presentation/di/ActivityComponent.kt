package ru.mpei.metro.presentation.di

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import dagger.BindsInstance
import dagger.Component
import ru.mpei.metro.domain.graph.MetroGraphProvider
import ru.mpei.metro.presentation.di.scopes.ActivityScope
import ru.mpei.metro.presentation.metro.MetroViewModelFactory

@Component(
    modules = [ActivityModule::class],
    dependencies = [ApplicationComponent::class],
)
@ActivityScope
interface ActivityComponent {
    fun context(): Context

    fun appCompatActivity(): AppCompatActivity

    fun activity(): Activity

    fun metroViewModelFactory(): MetroViewModelFactory

    fun metroGraphProvider(): MetroGraphProvider

    @Component.Builder
    interface Builder {
        fun applicationComponent(applicationComponent: ApplicationComponent): Builder

        @BindsInstance
        fun activity(activity: AppCompatActivity): Builder

        fun build(): ActivityComponent
    }
}
