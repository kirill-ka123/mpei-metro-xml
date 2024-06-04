package ru.mpei.metro.data.repository

import ru.mpei.metro.data.cache.DiskCaching
import ru.mpei.metro.data.cache.InMemoryCaching
import ru.mpei.metro.data.cache.model.CacheResult
import ru.mpei.metro.data.cache.model.DecodeResult
import ru.mpei.metro.presentation.di.scopes.ApplicationScope
import java.io.InputStream
import javax.inject.Inject

@ApplicationScope
class CachingRepository @Inject constructor(
    private val inMemoryCaching: InMemoryCaching,
    private val diskCaching: DiskCaching,
) {
    fun <T> load(key: String, decoder: (InputStream) -> T): CacheResult<T> {
        return inMemoryCaching.get(
            key = key,
        ) ?: diskCaching.get(
            key = key,
            decoder = decoder,
        ).also {
            inMemoryCaching.save(
                key = key,
                data = it.resource,
            )
        }
    }

    fun <R, F> save(
        key: String,
        data: InputStream,
        decoder: (InputStream) -> DecodeResult<R, F>,
    ): DecodeResult<R, F> {
        val parsedResponse: DecodeResult<R, F> = diskCaching.save(
            key = key,
            data = data,
            decoder = decoder,
        )
        if (parsedResponse is DecodeResult.Success) {
            inMemoryCaching.save(
                key = key,
                data = parsedResponse.result,
            )
        }
        return parsedResponse
    }

    fun invalidate() {
        inMemoryCaching.clear()
        diskCaching.clear()
    }
}
