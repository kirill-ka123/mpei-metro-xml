package ru.mpei.metro.presentation.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import ru.mpei.metro.presentation.di.scopes.ApplicationScope

@Module(includes = [DatabaseModule::class, NetworkModule::class, CacheModule::class])
interface ApplicationModule {
    companion object {
        @Provides
        @ApplicationScope
        fun provideApplicationContext(application: Application): Context {
            return application.applicationContext
        }
    }
}
