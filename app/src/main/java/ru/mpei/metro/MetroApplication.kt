package ru.mpei.metro

import android.app.Application
import ru.mpei.metro.presentation.di.ApplicationComponent
import ru.mpei.metro.presentation.di.DaggerApplicationComponent

open class MetroApplication : Application() {
    open lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
    }
}
