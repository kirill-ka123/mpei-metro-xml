package ru.mpei.metro.presentation.metro.di

import android.app.Activity
import android.content.Context
import android.view.View
import dagger.BindsInstance
import dagger.Component
import ru.mpei.metro.common.DiConstants
import ru.mpei.metro.domain.graph.MetroGraphProvider
import ru.mpei.metro.presentation.common.FragmentOnCreateViewListener
import ru.mpei.metro.presentation.di.ActivityComponent
import ru.mpei.metro.presentation.metro.BranchSplineConstructor
import ru.mpei.metro.presentation.metro.MetroViewModel
import javax.inject.Named

@Component(
    modules = [MetroFragmentModule::class],
    dependencies = [ActivityComponent::class],
)
@MetroFragmentScope
interface MetroFragmentComponent {
    fun context(): Context

    fun activity(): Activity

    fun onCreateViewListeners(): Set<FragmentOnCreateViewListener>

    fun metroGraphProvider(): MetroGraphProvider

    fun branchSplineConstructor(): BranchSplineConstructor

    @Component.Builder
    interface Builder {
        fun activityComponent(activityComponent: ActivityComponent): Builder

        @BindsInstance
        fun rootView(@Named(DiConstants.METRO_FRAGMENT_ROOT_VIEW) rootView: View): Builder

        @BindsInstance
        fun metroViewModel(metroViewModel: MetroViewModel): Builder

        fun build(): MetroFragmentComponent
    }
}
