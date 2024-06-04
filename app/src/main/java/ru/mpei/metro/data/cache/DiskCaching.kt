package ru.mpei.metro.data.cache

import android.util.Log
import ru.mpei.metro.common.DiConstants
import ru.mpei.metro.data.cache.model.CacheResult
import ru.mpei.metro.data.cache.model.DecodeResult
import ru.mpei.metro.data.cache.utils.copyingTo
import ru.mpei.metro.presentation.di.scopes.ApplicationScope
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Named

private const val TAG = "[CachingRepository]"
private const val LIFETIME_MILS = 1000000L

@ApplicationScope
class DiskCaching @Inject constructor(
    @Named(DiConstants.CACHE_DIRECTORY)
    private val directory: File,
    private val fallbackDataProvider: FallbackDataProvider,
) {
    private fun getDiskDataStorageByKey(key: String) = DiskDataStorage(File(directory, key))

    fun <R, F> save(
        key: String,
        data: InputStream,
        decoder: (InputStream) -> DecodeResult<R, F>
    ): DecodeResult<R, F> {
        val tmpResponseStream = ByteArrayOutputStream()
        val decoded = decoder(data.copyingTo(tmpResponseStream))
        if (decoded is DecodeResult.Success) {
            try {
                getDiskDataStorageByKey(key).save(tmpResponseStream.toByteArray())
            } catch (error: Throwable) {
                Log.e(TAG, "Error while saving to storage", error)
            }
        }
        return decoded
    }

    fun <T> get(key: String, decoder: (InputStream) -> T): CacheResult<T> {
        val data = try {
            getDiskDataStorageByKey(key).load() ?: fallbackDataProvider.getFallbackData()
        } catch (error: Throwable) {
            Log.e(TAG, "Error while loading from storage", error)
            fallbackDataProvider.getFallbackData()
        }

        return CacheResult(
            resource = decoder(data),
            freshUntilTimestamp = System.currentTimeMillis() + LIFETIME_MILS,
        )
    }

    fun delete(key: String) {
        try {
            getDiskDataStorageByKey(key).clear()
        } catch (error: Throwable) {
            Log.e(TAG, "Error while deleting from storage", error)
        }
    }

    fun clear() = directory.deleteRecursively()
}
