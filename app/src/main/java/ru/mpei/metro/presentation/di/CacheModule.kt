package ru.mpei.metro.presentation.di

import android.app.Application
import dagger.Module
import dagger.Provides
import ru.mpei.metro.R
import ru.mpei.metro.common.DiConstants
import ru.mpei.metro.data.cache.FallbackDataProvider
import ru.mpei.metro.presentation.di.scopes.ApplicationScope
import java.io.File
import javax.inject.Named

@Module
class CacheModule {
    @Provides
    @ApplicationScope
    @Named(DiConstants.CACHE_DIRECTORY)
    fun provideCacheDirectory(
        application: Application,
    ): File {
        return File(application.filesDir, CACHE_FILE_NAME)
    }

    @Provides
    @ApplicationScope
    fun provideFallbackDataProvider(
        application: Application,
    ): FallbackDataProvider {
        return FallbackDataProvider {
            application.resources.openRawResource(R.raw.default_metro_graph)
        }
    }

    companion object {
        const val CACHE_FILE_NAME = "metro"
    }
}
