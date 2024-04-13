package ru.mpei.metro.presentation.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.mpei.metro.data.history.MetroDatabase
import ru.mpei.metro.data.repository.CitiesRepositoryImpl
import ru.mpei.metro.domain.repository.CitiesRepository
import ru.mpei.metro.presentation.di.scopes.ApplicationScope

@Module
interface ApplicationModule {

    @Binds
    fun bindCitiesRepository(i: CitiesRepositoryImpl): CitiesRepository

    companion object {
        @Provides
        @ApplicationScope
        fun provideApplicationContext(application: Application): Context {
            return application.applicationContext
        }

        @Provides
        @ApplicationScope
        fun provideMetroDatabase(application: Application): MetroDatabase {
            return Room.databaseBuilder(
                application.applicationContext,
                MetroDatabase::class.java,
                "history_routes_database.dp"
            ).build()
        }
    }
}
