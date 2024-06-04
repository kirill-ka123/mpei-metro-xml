package ru.mpei.metro.data.cache

import ru.mpei.metro.data.cache.model.CacheResult
import ru.mpei.metro.presentation.di.scopes.ApplicationScope
import java.util.Collections
import javax.inject.Inject

private const val LIFETIME_MILS = 1000000L

@ApplicationScope
class InMemoryCaching @Inject constructor() {
    private val storage = Collections.synchronizedMap(HashMap<String, Any?>())

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: String): CacheResult<T>? {
        return (storage[key] as? T?)?.let { data ->
            CacheResult(
                resource = data,
                freshUntilTimestamp = System.currentTimeMillis() + LIFETIME_MILS,
            )
        }
    }

    fun <T> save(key: String, data: T) {
        storage[key] = data
    }

    fun delete(key: String) {
        storage.remove(key)
    }

    fun clear() {
        storage.clear()
    }
}
