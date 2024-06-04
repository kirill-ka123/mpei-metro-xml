package ru.mpei.metro.presentation.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.mpei.metro.data.db.history.HistoryRouteDatabase
import ru.mpei.metro.presentation.di.scopes.ApplicationScope

@Module
class DatabaseModule {
    @Provides
    @ApplicationScope
    fun provideHistoryRouteDatabase(application: Application): HistoryRouteDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            HistoryRouteDatabase::class.java,
            HISTORY_ROUTES_DATABASE_NAME
        ).build()
    }

    companion object {
        const val HISTORY_ROUTES_DATABASE_NAME = "history_routes_database.dp"
    }
}
